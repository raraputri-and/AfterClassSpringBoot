package com.metrodataacademy.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReqLoginDto {
    @NotBlank
    private String emailorusername;
    @NotBlank
    private String password;
}
