package com.metrodataacademy.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResBaseDto {
    private Object data;
    private String message;
}
