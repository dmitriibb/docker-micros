package com.dmbb.cafe.controller;

import com.dmbb.cafe.service.ServiceB;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/b")
@RequiredArgsConstructor
public class ServiceBController {

    private final ServiceB serviceB;

    @GetMapping("/info/eureka")
    public Map<String, Object> getInfoViaEureka() {
        return serviceB.getInfoViaEureka();
    }

    @GetMapping("/info/hystrix")
    public Map<String, Object> getInfoViaHystrix(@RequestParam int delay, @RequestParam int timeout) {
        return serviceB.getInfoViaHystrix(delay, timeout);
    }

    @GetMapping("/info/error")
    public Map<String, Object> getInfoError() {
        return serviceB.getInfoError();
    }


}
