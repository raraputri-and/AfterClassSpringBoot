package com.metrodataacademy.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResArticlesDto {
    private String id;
    private String title;
    private String imageUrl;
    private String createdAt;
    private List<ResCategoriesDto> categories;
}
