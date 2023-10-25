package com.metrodataacademy.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqCreateThreadsDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
