package com.dmbb.springappa.service.impl;

import com.dmbb.springappa.exceptions.ServiceARuntimeException;
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

import static com.dmbb.springappa.constants.Constants.SERVICE_B_NAME;
import static com.dmbb.springappa.constants.Constants.SERVICE_C_NAME;

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

    public Mono<String> getStringReactiveBalanced(String serviceName, String apiUrl) {
        log.info("request for: " + apiUrl);
        String fullUrl = "http://" + serviceName + "/" + apiUrl;
        return webClientLoadBalanced.get()
                .uri(fullUrl)
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> getStringReactive(String serviceName, String apiUrl) {
        String baseUrl = getServiceBaseURL(serviceName);
        String fullUrl = baseUrl+ apiUrl;
        log.info("reactive request for: " + apiUrl);

        return webClient.get()
                .uri(fullUrl)
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public List<String> cookFoodInServiceB(List<Food> foodList) {
        String baseUrl = getServiceBaseURL(SERVICE_B_NAME);
        String fullUrl = baseUrl + "/food/cook";

        RequestEntity requestEntity = new RequestEntity(new FoodListDTO(foodList), HttpMethod.POST, getURI(fullUrl));
        return restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<String>>() {}).getBody();
    }

    @Override
    public TrayDTO getTrayFromServiceC(List<String> foodList) {
        String baseUrl = getServiceBaseURL(SERVICE_C_NAME);
        String fullUrl = baseUrl + "/food/put-on-tray";
        RequestEntity requestEntity = new RequestEntity(foodList, HttpMethod.POST, getURI(fullUrl));
        return restTemplate.exchange(requestEntity, TrayDTO.class).getBody();
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
