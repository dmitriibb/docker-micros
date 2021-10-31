package com.dmbb.springappa.controller;

import com.dmbb.springappa.dto.InfoDTO;
import com.dmbb.springappa.service.ExternalInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final ExternalInfoService externalInfoService;

    @Value("${eureka.client.serviceUrl.defaultZone}")
    private String eurekaDefaultZone;

    @GetMapping
    public InfoDTO getInfo() {
        return new InfoDTO("getInfo()", "response from spring-app-a");
    }

    @GetMapping("/app-b-info")
    public String getAppBInfo() {
        return externalInfoService.getInfoFromApplicationB();
    }

    @GetMapping("/app-b-info-direct")
    public String getAppBInfoDirect() {
        return externalInfoService.getInfoFromApplicationBDirect();
    }

    @GetMapping("/arguments")
    public Map<String, String> getArguments() {
        Map<String, String> map = new HashMap<>();
        map.put("eurekaDefaultZone", eurekaDefaultZone);
        return map;
    }

}
