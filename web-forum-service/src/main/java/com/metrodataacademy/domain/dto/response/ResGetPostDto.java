package com.metrodataacademy.domain.dto.response;

import lombok.Data;

@Data
public class ResGetPostDto {
    private String threadsId;
    private String postId;
    private String content;
    private ResStagingUserDto author;

}
