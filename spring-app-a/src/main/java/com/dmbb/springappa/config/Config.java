package com.dmbb.springappa.config;

import com.dmbb.springappa.service.RestRequestService;
import com.dmbb.springappa.service.hystrix.ServiceBHystrixCommand;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /*@Bean
    public ServiceBHystrixCommand serviceBHystrixCommand(RestRequestService restRequestService) {
        HystrixCommand.Setter config = HystrixCommand
                .Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceGroup1"));

        HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties.Setter();
        commandProperties.withExecutionTimeoutInMilliseconds(5000);
        config.andCommandPropertiesDefaults(commandProperties);

        return new ServiceBHystrixCommand(config, restRequestService);
    }*/

}
