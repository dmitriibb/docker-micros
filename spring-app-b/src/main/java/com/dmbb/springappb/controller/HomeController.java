package com.dmbb.springappb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String getInto() {
        return "Info response from spring-app-b";
    }

    @GetMapping("/for-service-a")
    public Map<String, Object> getInfoForServiceA(@RequestParam(required = false) Integer delay) throws InterruptedException {

        if (delay != null)
            Thread.sleep(delay);

        Map<String, Object> map = new HashMap<>();

        map.put("name", "dima");
        map.put("age", 27);
        map.put("fruits", Arrays.asList("banana", "apple", "orange"));

        return map;
    }


}
