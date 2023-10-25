package com.metrodataacademy.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ReqCreateArticlesDto {
    @NotBlank
    private String title;

    @NotBlank
    private String body;

    @NotBlank
    private String banner;

    @NotBlank
    private String imageUrl;

    private List<ReqListCategoryDto> categories;
}
