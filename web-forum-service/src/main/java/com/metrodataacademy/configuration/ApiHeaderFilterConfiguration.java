package com.metrodataacademy.configuration;

import com.metrodataacademy.domain.dto.AuthorizationDto;
import com.metrodataacademy.service.intrf.AuthServiceRestTemplate;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@Slf4j
public class ApiHeaderFilterConfiguration extends OncePerRequestFilter {

    private final AuthServiceRestTemplate authService;

    public ApiHeaderFilterConfiguration(AuthServiceRestTemplate authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        AuthorizationDto authDto = authService.getLogin(token);
        if (authDto == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        request.setAttribute("User", authDto);
        filterChain.doFilter(request, response);
    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return "/no-Header".equals(path) || "/article/detail".equals(path) || "/article/list".equals(path);
    }
}
