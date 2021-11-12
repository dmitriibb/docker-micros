package com.dmbb.cafe.service.impl;

import com.dmbb.cafe.service.RestRequestService;
import com.dmbb.cafe.service.ServiceB;
import com.dmbb.cafe.service.hystrix.ServiceBHystrixCommand;
import com.google.common.collect.ImmutableMap;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceBImpl implements ServiceB {

    private static final String APP_B_NAME = "spring-app-b";
    private static final String PARAM_DELAY = "delay";

    @Value("${spring-app-b.host}")
    private String serviceBHost;

    private final RestRequestService restRequestService;

    @Override
    public Map<String, Object> getInfoViaEureka() {
        log.info("calling spring-app-b via eureka");
        Map<String, Object> params = ImmutableMap.of(PARAM_DELAY, 0);
        return restRequestService.getMap(APP_B_NAME, "home/for-service-a", params);
    }

    @Override
    public Map<String, Object> getInfoDirect() {
        log.info("calling spring-app-b directly");
        Map<String, Object> params = ImmutableMap.of(PARAM_DELAY, 0);
        return restRequestService.getMap("http://" + serviceBHost + ":8082/home/for-service-a", params);
    }

    @Override
    public Map<String, Object> getInfoViaHystrix(int delay, int timeout) {
        ServiceBHystrixCommand serviceBHystrixCommand = serviceBHystrixCommand(delay, timeout);
        log.info("calling spring-app-b using hystrix");
        return serviceBHystrixCommand.execute();
    }

    private ServiceBHystrixCommand serviceBHystrixCommand(int delay, int timeout) {
        HystrixCommand.Setter config = HystrixCommand
                .Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("sericeBgroup_" + delay + "_" + timeout))
                .andCommandKey(HystrixCommandKey.Factory.asKey("ServiceBHystrixCommand" + delay + "_" + timeout));


        HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties.Setter();
        commandProperties.withExecutionTimeoutInMilliseconds(timeout);
        config.andCommandPropertiesDefaults(commandProperties);

        return new ServiceBHystrixCommand(config, restRequestService, delay);
    }

    @Override
    public Map<String, Object> getInfoError() {
        log.info("Throwing test runtime exception in ServiceB");
        throw new RuntimeException("Test error alala");
    }
}
