package com.metrodataacademy.domain.dto.request;

import com.metrodataacademy.domain.dto.customvalidator.ValidSortType;
import com.metrodataacademy.domain.dto.customvalidator.ValidThreadsFilterBy;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReqGetArticlesDto {
    private String title = "";
    private String category = "";

    @NotNull
    @Min(1)
    private int page;
}
