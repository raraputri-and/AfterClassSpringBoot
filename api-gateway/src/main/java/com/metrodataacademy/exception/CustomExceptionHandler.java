package com.metrodataacademy.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.net.ConnectException;

@Component
@Order(-2)  // Make sure this handler has higher precedence than DefaultWebExceptionHandler
public class CustomExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof ConnectException) {
            exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);  // Set the status to 404
            return exchange.getResponse().setComplete();  // Complete the response
        }
        return Mono.error(ex);  // If not a ConnectException, continue with the default error handling
    }
}