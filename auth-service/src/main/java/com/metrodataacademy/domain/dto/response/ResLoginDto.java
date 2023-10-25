package com.metrodataacademy.domain.dto.response;

import lombok.Data;

@Data
public class ResLoginDto {
    private String token;
    private String refreshToken;
    private String exp;
}
