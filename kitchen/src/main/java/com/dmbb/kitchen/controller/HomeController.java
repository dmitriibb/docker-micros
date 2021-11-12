package com.dmbb.kitchen.controller;

import com.dmbb.kitchen.service.RestRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final RestRequestService restRequestService;

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

    @GetMapping("/info/{serviceName}")
    public String getOtherServiceInfo(@PathVariable String serviceName) {
        return restRequestService.getHomeInfo(serviceName);
    }


}
