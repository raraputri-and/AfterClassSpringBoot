package com.metrodataacademy.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolesDto {
    private String name;
    private String id;
}
