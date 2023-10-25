package com.metrodataacademy.service.impl;

import com.metrodataacademy.domain.dto.AuthorizationDto;
import com.metrodataacademy.domain.dto.response.ResBaseValidateTokenDto;
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

    /**
     * the url must be defined on application.properties
     * make sure the API gateway it's already configured
     */
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
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try{
            // request detail
            ResponseEntity<ResBaseValidateTokenDto> responseEntity = restTemplate.exchange(url + "/auth/validate-token", HttpMethod.POST, entity, ResBaseValidateTokenDto.class);
            AuthorizationDto authDto = new AuthorizationDto();
            System.out.println(responseEntity.getBody().getMessage());
            if (responseEntity.getStatusCode().value() != 200){
                return authDto;
            }


            // mapping response to dto
            authDto = Objects.requireNonNull(responseEntity.getBody().getData());
            return authDto;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
