package com.dmbb.springappa.controller;

import com.dmbb.springappa.model.dto.InfoDTO;

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

    @Value("${eureka.client.serviceUrl.defaultZone}")
    private String eurekaDefaultZone;

    @GetMapping
    public String getInfo() {
        return "Info response from spring-app-a";
    }


    @GetMapping("/arguments")
    public Map<String, String> getArguments() {
        Map<String, String> map = new HashMap<>();
        map.put("eurekaDefaultZone", eurekaDefaultZone);
        return map;
    }

}
