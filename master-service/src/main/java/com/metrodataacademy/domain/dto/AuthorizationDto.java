package com.metrodataacademy.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthorizationDto {
    private String id;
    private String name;
    private String email;
    private String profilePicture;
    private List<String> roles;
}
