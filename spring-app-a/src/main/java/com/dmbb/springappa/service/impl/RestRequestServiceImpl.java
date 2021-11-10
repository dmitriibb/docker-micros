package com.dmbb.springappa.service.impl;

import com.dmbb.springappa.constants.Constants;
import com.dmbb.springappa.exceptions.ServiceARuntimeException;
import com.dmbb.springappa.model.RestCallSettings;
import com.dmbb.springappa.model.dto.FoodListDTO;
import com.dmbb.springappa.model.dto.TrayDTO;
import com.dmbb.springappa.model.entity.Food;
import com.dmbb.springappa.service.RestRequestService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static com.dmbb.springappa.constants.Constants.*;

@Service
@Slf4j
public class RestRequestServiceImpl implements RestRequestService {

    private final RestTemplate restTemplate;

    private final RestTemplate restTemplateLoadBalanced;

    private final EurekaClient eurekaClient;

    private final WebClient webClient;

    private final WebClient webClientLoadBalanced;

    public RestRequestServiceImpl(@Qualifier("restTemplate") RestTemplate restTemplate,
                                  @Qualifier("restTemplateLoadBalanced") RestTemplate restTemplateLoadBalanced,
                                  @Qualifier("webClient") WebClient webClient,
                                  @Qualifier("webClientLoadBalanced") WebClient webClientLoadBalanced,
                                  EurekaClient eurekaClient) {
        this.restTemplate = restTemplate;
        this.restTemplateLoadBalanced = restTemplateLoadBalanced;
        this.eurekaClient = eurekaClient;
        this.webClient = webClient;
        this.webClientLoadBalanced = webClientLoadBalanced;
    }

    @Override
    public Map<String, Object> getMap(String fullUrl, Map<String, Object> params) {
        String urlWithParams = addUrlParams(fullUrl, params);
        return restTemplate.exchange(urlWithParams, HttpMethod.GET, null, Map.class, new HashMap<>()).getBody();
    }

    @Override
    public Map<String, Object> getMap(String serviceName, String apiUrl, Map<String, Object> params) {
        String baseUrl = getServiceBaseURL(SERVICE_B_NAME);
        String fullUrl = baseUrl + apiUrl;
        String urlWithParams = addUrlParams(fullUrl, params);
        return restTemplate.exchange(urlWithParams, HttpMethod.GET, null, Map.class, new HashMap<>()).getBody();
    }

    @Override
    public String getString(String serviceName, String apiUrl) {
        log.info("request for: " + apiUrl);
        String fullUrl = "http://" + serviceName + "/" + apiUrl;
        return restTemplateLoadBalanced.exchange(fullUrl, HttpMethod.GET, null, String.class, new HashMap<>()).getBody();
    }

    @Override
    public Mono<String> getString(String serviceName, String api, RestCallSettings settings) {
        String fullUrl = getFullUrl(serviceName, api, settings);
        log.info("calling: " + fullUrl);

        return getWebClient(settings)
                .get()
                .uri(fullUrl)
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public List<String> cookFood(List<Food> foodList, RestCallSettings settings) {
        String fullUrl = getFullUrl(SERVICE_B_NAME, "food/cook", settings);

        RequestEntity requestEntity = new RequestEntity(new FoodListDTO(foodList), HttpMethod.POST, getURI(fullUrl));
        return getRestTemplate(settings)
                .exchange(requestEntity, new ParameterizedTypeReference<List<String>>() {})
                .getBody();
    }

    @Override
    public Mono<List<String>> cookFoodReactive(List<Food> foodList, RestCallSettings settings) {
        String fullUrl = getFullUrl(SERVICE_B_NAME, "food/cook", settings);

        return getWebClient(settings)
                .post()
                .uri(fullUrl)
                .bodyValue(new FoodListDTO(foodList))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {});

    }

    @Override
    public TrayDTO getTrayWithCookedFood(List<String> foodList, RestCallSettings settings) {
        String fullUrl = getFullUrl(SERVICE_C_NAME, "food/put-on-tray", settings);

        RequestEntity requestEntity = new RequestEntity(foodList, HttpMethod.POST, getURI(fullUrl));
        return getRestTemplate(settings)
                .exchange(requestEntity, TrayDTO.class)
                .getBody();
    }

    private WebClient getWebClient(RestCallSettings settings) {
        return settings.isLoadBalancer() ? webClientLoadBalanced : webClient;
    }

    private RestTemplate getRestTemplate(RestCallSettings settings) {
        return settings.isLoadBalancer() ? restTemplateLoadBalanced : restTemplate;
    }

    private String getFullUrl(String serviceName, String api, RestCallSettings settings) {
        if (settings.isLoadBalancer())
            return getFullUrlWithLoadBalancer(serviceName, api, settings);
        else
            return getFullUrlWithDiscoveryClient(serviceName, api, settings);
    }

    private String getFullUrlWithLoadBalancer(String serviceName, String api, RestCallSettings settings) {
        if (settings.isGateway())
            return "http://" + Constants.GATEWAY_SERVICE_NAME + "/" + serviceName + "/" + api;
        else
            return "http://" + serviceName + "/" + api;
    }

    private String getFullUrlWithDiscoveryClient(String serviceName, String api, RestCallSettings settings) {
        if (settings.isGateway())
            return getServiceBaseURL(GATEWAY_SERVICE_NAME) + serviceName + "/" + api;
        else
            return getServiceBaseURL(serviceName)  + api;
    }

    private String getServiceBaseURL(String serviceName) {
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(serviceName, false);
        return instanceInfo.getHomePageUrl();
    }

    private String addUrlParams(String url, Map<String, Object> params) {
        if (CollectionUtils.isEmpty(params)) return url;
        StringJoiner joiner = new StringJoiner("&");
        params.forEach((key, value) -> {
            if (value != null)
                joiner.add(key + "=" + value);
        });
        return joiner.length() == 0 ? url : url + "?" + joiner;
    }

    private URI getURI(String url) {
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            log.error(e.getMessage(), e);
            throw new ServiceARuntimeException(e.getMessage());
        }
    }
}
