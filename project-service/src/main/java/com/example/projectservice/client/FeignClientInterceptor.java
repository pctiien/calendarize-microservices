package com.example.projectservice.client;

import com.example.projectservice.security.JwtTokenFilter;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = JwtTokenFilter.tokenHolder.get();
        if (token != null) {
            requestTemplate.header("Authorization", "Bearer " + token);
        }
    }
}
