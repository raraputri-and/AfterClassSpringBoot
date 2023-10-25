package com.metrodataacademy.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResDetailArticleDto {
    private String id;
    private String title;
    private String content;
    private String slug;
    private String caption;
    private ResStagingUserDto author;
    private Integer counter;
    private String imageUrl;
    private String createdAt;
    private String updatedAt;
    private List<ResCategoriesDto> categories;
}
