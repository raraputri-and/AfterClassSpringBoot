package com.metrodataacademy.service.impl;

import com.metrodataacademy.domain.dto.AuthorizationDto;
import com.metrodataacademy.domain.dto.response.ResBaseValidateToken;
import com.metrodataacademy.service.intrf.AuthServiceRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Slf4j
public class AuthServiceRestTemplateImpl implements AuthServiceRestTemplate {

    @Value("http://localhost:8080/api/v1/auth-service")
    private String url;

    private final RestTemplate restTemplate;

    public AuthServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public AuthorizationDto getLogin(String authToken) {
        // set header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        headers.setBearerAuth(authToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try{
            // request detail
            ResponseEntity<ResBaseValidateToken> responseEntity = restTemplate.exchange(url + "auth/validate-token", HttpMethod.POST, entity, ResBaseValidateToken.class);
            AuthorizationDto authDto = new AuthorizationDto();
            if (responseEntity.getStatusCode().value() != 200){
                return authDto;
            }

            // mapping response to dto
            authDto = Objects.requireNonNull(responseEntity.getBody().getData());
            return authDto;
        }catch (Exception e){
            return null;
        }
    }
}
