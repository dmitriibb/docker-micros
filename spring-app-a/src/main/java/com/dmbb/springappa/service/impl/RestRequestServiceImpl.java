package com.dmbb.springappa.service.impl;

import com.dmbb.springappa.service.RestRequestService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
public class RestRequestServiceImpl implements RestRequestService {

    private static final String PARAM_DELAY = "delay";

    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    @Override
    public Map<String, Object> getMap(String fullUrl, Map<String, Object> params) {
        String urlWithParams = addUrlParams(fullUrl, params);
        return restTemplate.exchange(urlWithParams, HttpMethod.GET, null, Map.class, new HashMap<>()).getBody();
    }

    @Override
    public Map<String, Object> getMap(String serviceName, String apiUrl, Map<String, Object> params) {
        InstanceInfo instanceInfo = eurekaClient.getApplication(serviceName).getInstances().get(0);
        String fullUrl = instanceInfo.getHomePageUrl() + apiUrl;
        String urlWithParams = addUrlParams(fullUrl, params);
        return restTemplate.exchange(urlWithParams, HttpMethod.GET, null, Map.class, new HashMap<>()).getBody();
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
}
