package com.metrodataacademy.domain.mapper;

import com.metrodataacademy.domain.dto.response.ResCategoriesDto;
import com.metrodataacademy.domain.entity.Categories;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriesMapper {
    ResCategoriesDto categoriesToResCategoriesDto(Categories categories);
}
