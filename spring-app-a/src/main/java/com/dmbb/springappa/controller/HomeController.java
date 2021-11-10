package com.dmbb.springappa.controller;

import com.dmbb.springappa.model.dto.InfoDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    @Value("${eureka.client.serviceUrl.defaultZone}")
    private String eurekaDefaultZone;

    @GetMapping
    public String getInfo() {
        return "Info response from spring-app-a";
    }

    @GetMapping("/test")
    public String test(@RequestHeader(required = false) String testHeader) {
        String response = "response from test: testHeader=" + testHeader;
        log.info(response);
        return response;
    }

    @GetMapping("/arguments")
    public Map<String, String> getArguments() {
        Map<String, String> map = new HashMap<>();
        map.put("eurekaDefaultZone", eurekaDefaultZone);
        return map;
    }

}
