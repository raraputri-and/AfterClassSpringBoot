package com.metrodataacademy.service.intrf;


import com.metrodataacademy.domain.dto.AuthorizationDto;

public interface AuthServiceRestTemplate {
    AuthorizationDto getLogin(String authToken);
}
