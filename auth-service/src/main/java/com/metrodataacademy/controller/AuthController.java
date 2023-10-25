package com.metrodataacademy.controller;

import com.metrodataacademy.domain.dto.request.ReqLoginDto;
import com.metrodataacademy.domain.dto.request.ReqRegisterDto;
import com.metrodataacademy.domain.dto.response.ResTemplateDto;
import com.metrodataacademy.service.interfaces.AuthService;
import com.metrodataacademy.service.interfaces.UserService;
import com.metrodataacademy.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResTemplateDto> register(@Valid @RequestBody ReqRegisterDto data) {
        return authService.register(data);
    }

    @PostMapping("/login")
    public ResponseEntity<ResTemplateDto> login(@Valid @RequestBody ReqLoginDto loginData, HttpServletResponse response) {
        return authService.login(loginData, response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResTemplateDto> refreshToken(@RequestBody String refreshToken) {
        return authService.refreshToken(refreshToken);
    }
}
