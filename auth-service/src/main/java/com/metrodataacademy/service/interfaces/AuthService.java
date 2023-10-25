package com.metrodataacademy.service.interfaces;

import com.metrodataacademy.domain.dto.request.ReqLoginDto;
import com.metrodataacademy.domain.dto.request.ReqRegisterDto;
import com.metrodataacademy.domain.dto.response.ResTemplateDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<ResTemplateDto> register(ReqRegisterDto data);

    ResponseEntity<ResTemplateDto> login(ReqLoginDto data, HttpServletResponse response);

    ResponseEntity<ResTemplateDto> refreshToken(String refreshToken);

    ResponseEntity<ResTemplateDto> validateToken(String authToken);
}
