package com.example.apigatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class ApigatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApigatewayServerApplication.class, args);
    }

    @Bean
    public RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/calendarize/user/**")
                        .filters(f -> f.rewritePath("/calendarize/user/(?<segment>.*)", "/api/user/$\\{segment}"))
                        .uri("lb://APPUSER-SERVICE"))
                .route(p -> p
                        .path("/calendarize/auth/**")
                        .filters(f -> f.rewritePath("/calendarize/auth/(?<segment>.*)", "/api/auth/$\\{segment}"))
                        .uri("lb://AUTH-SERVICE"))
                .route(p -> p
                        .path("/calendarize/life/tasks/**")
                        .filters(f -> f.rewritePath("/calendarize/life/tasks/(?<segment>.*)", "/api/life/tasks/$\\{segment}"))
                        .uri("lb://LIFETASK-SERVICE"))
                .route(p -> p
                        .path("/calendarize/projects/**")
                        .filters(f -> f.rewritePath("/calendarize/projects/(?<segment>.*)", "/api/projects/$\\{segment}"))
                        .uri("lb://PROJECT-SERVICE"))
                .build();
    }
}
