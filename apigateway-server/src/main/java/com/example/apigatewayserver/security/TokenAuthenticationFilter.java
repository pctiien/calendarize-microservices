package com.example.apigatewayserver.security;

import com.example.apigatewayserver.client.AuthWebClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TokenAuthenticationFilter implements WebFilter {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    private final TokenProvider tokenProvider;
    private final AuthWebClient authWebClient;

    @Override
    public Mono<Void> filter( ServerWebExchange exchange,  WebFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        if (path.startsWith("/calendarize/auth/")) {
            return chain.filter(exchange);
        }

        return Mono.just(exchange)
                .flatMap(this::authenticate)
                .flatMap(authentication -> {
                    if(authentication!=null)
                    {
                        return chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                    }else{
                        return chain.filter(exchange);
                    }
                })
                .onErrorResume(e -> {
                    logger.error("Filter error", e);
                    return Mono.empty();
                });
    }


    private Mono<Authentication> authenticate(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String jwt = getJwtFromRequest(request);

        if (jwt != null && tokenProvider.validateToken(jwt)) {
            Long userId = tokenProvider.getUserIdFromToken(jwt);
            return authWebClient.getUserById(userId)
                    .flatMap(user -> {
                        UserDetails userDetails = UserPrincipal.create(user);
                        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        return Mono.just(authentication);
                    })
                    .onErrorResume(e -> {
                        logger.error("Authentication error", e);
                        return Mono.empty();
                    });
        }
        return Mono.empty();
    }

    private String getJwtFromRequest(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String bearerToken = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
