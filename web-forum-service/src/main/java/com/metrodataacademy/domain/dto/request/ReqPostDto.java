package com.metrodataacademy.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReqPostDto {
    @NotNull
    private String idThreads;

    @NotBlank
    private String content;
}
