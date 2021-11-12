package com.dmbb.kitchen.service.impl;

import com.dmbb.kitchen.exception.KitchenRuntimeException;
import com.dmbb.kitchen.service.RestRequestService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestRequestServiceImpl implements RestRequestService {

    public final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    @Override
    public Map<String, Object> getMap(String serviceName, String apiUrl, Map<String, Object> params) {
        return null;
    }

    @Override
    public String getHomeInfo(String serviceName) {
        String baseUrl = getServiceBaseURL(serviceName);
        return restTemplate.exchange(baseUrl + "home", HttpMethod.GET, null , String.class, new HashMap<>()).getBody();
    }

    private String getServiceBaseURL(String serviceName) {
        InstanceInfo instanceInfo = eurekaClient.getApplication(serviceName).getInstances().get(0);
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
            throw new KitchenRuntimeException(e.getMessage());
        }
    }

}
