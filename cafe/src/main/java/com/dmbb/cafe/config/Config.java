package com.dmbb.cafe.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceThreadPoolTaskExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class Config {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplateLoadBalanced() {
        return new RestTemplate();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

    @Bean
    public WebClient webClientLoadBalanced(WebClient.Builder builder) {
        return builder.build();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    //With standard java executor Spring Sleuth doesn't create spans for different threads, so we need this Lazy thing
    @Bean(name = "concurrentServiceExecutor")
    public ThreadPoolTaskExecutor concurrentServiceExecutor(BeanFactory beanFactory) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.setMaxPoolSize(4);
        threadPoolTaskExecutor.initialize();

        return new LazyTraceThreadPoolTaskExecutor(beanFactory, threadPoolTaskExecutor);
    }

}
