package com.dmbb.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    private static final String APP_A_URL ="/spring-app-a";
    private static final String APP_B_URL ="/spring-app-b";
    private static final String APP_C_URL ="/spring-app-c";

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> routeServiceEureka(p, APP_A_URL))
                .route(p -> routeServiceEureka(p, APP_B_URL))
                .route(p -> routeServiceEureka(p, APP_C_URL))
                .build();
    }

    private Buildable<Route> routeService(PredicateSpec p, String appUrlPredicate, String appHost) {
        return p.path(appUrlPredicate + "/**")
                .filters(f -> f.rewritePath(appUrlPredicate + "/(?<segment>)","/${segment}"))
                .uri("http://" + appHost);
    }

    private Buildable<Route> routeServiceEureka(PredicateSpec p, String appUrlPredicate) {
        return p.path(appUrlPredicate + "/**")
                .filters(f -> f.rewritePath(appUrlPredicate + "/(?<segment>)","/${segment}"))
                .uri("lb:/" + appUrlPredicate);
    }


}
