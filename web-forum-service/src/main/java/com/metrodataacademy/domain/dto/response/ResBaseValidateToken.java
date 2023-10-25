package com.metrodataacademy.domain.dto.response;

import com.metrodataacademy.domain.dto.AuthorizationDto;
import lombok.Data;

@Data
public class ResBaseValidateToken {
    private AuthorizationDto data;
    private String message;
}
