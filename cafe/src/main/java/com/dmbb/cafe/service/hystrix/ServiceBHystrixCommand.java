package com.dmbb.cafe.service.hystrix;

import com.dmbb.cafe.service.RestRequestService;
import com.google.common.collect.ImmutableMap;
import com.netflix.hystrix.HystrixCommand;


import java.util.Map;

public class ServiceBHystrixCommand extends HystrixCommand<Map<String, Object>> {

    private final RestRequestService restRequestService;
    private int delay;

    public ServiceBHystrixCommand(Setter config, RestRequestService restRequestService, int delay) {
        super(config);
        this.restRequestService = restRequestService;
        this.delay = delay;
    }

    @Override
    protected Map<String, Object> run() throws Exception {
        Map<String, Object> params = ImmutableMap.of("delay", delay);
        return restRequestService.getMap("spring-app-b", "home/for-service-a", params);
    }
}
