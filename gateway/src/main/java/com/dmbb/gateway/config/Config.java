package com.dmbb.gateway.config;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    private static final String SERVICE_CAFE ="/cafe";
    private static final String SERVICE_KITCHEN ="/kitchen";
    private static final String SERVICE_STEWARD ="/steward";

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> routeServiceEureka(p, SERVICE_CAFE))
                .route(p -> routeServiceEureka(p, SERVICE_KITCHEN))
                .route(p -> routeServiceEureka(p, SERVICE_STEWARD))
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
