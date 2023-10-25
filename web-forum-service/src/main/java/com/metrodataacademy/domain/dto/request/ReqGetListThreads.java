package com.metrodataacademy.domain.dto.request;

import com.metrodataacademy.domain.dto.customvalidator.ValidSortType;
import com.metrodataacademy.domain.dto.customvalidator.ValidThreadsFilterBy;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqGetListThreads {
    @NotNull
    @Min(1)
    private int page;

    @ValidThreadsFilterBy
    private String filterBy = "";

    @ValidSortType
    private String sortBy = "";

    private String keyword = "";
}
