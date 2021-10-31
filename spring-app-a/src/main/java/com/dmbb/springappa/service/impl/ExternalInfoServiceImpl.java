package com.dmbb.springappa.service.impl;

import com.dmbb.springappa.service.ExternalInfoService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@Slf4j
public class ExternalInfoServiceImpl implements ExternalInfoService {

    private static final String APP_B_NAME = "spring-app-b";

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getInfoFromApplicationB() {
        InstanceInfo instanceInfo = eurekaClient.getApplication(APP_B_NAME).getInstances().get(0);

        String appBURL = instanceInfo.getHomePageUrl() + "home";
        String response = restTemplate.exchange(appBURL, HttpMethod.GET, null, String.class, new HashMap<>()).getBody();

        return response;
    }

    @Override
    public String getInfoFromApplicationBDirect() {
        String appBURL = "http://spring-app-b:8082/home";
        return restTemplate.exchange(appBURL, HttpMethod.GET, null, String.class, new HashMap<>()).getBody();
    }
}
