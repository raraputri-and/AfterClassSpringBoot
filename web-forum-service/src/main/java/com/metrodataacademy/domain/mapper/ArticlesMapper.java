package com.metrodataacademy.domain.mapper;

import com.metrodataacademy.domain.dto.request.ReqCreateArticlesDto;
import com.metrodataacademy.domain.dto.response.ResArticlesDto;
import com.metrodataacademy.domain.dto.response.ResDetailArticleDto;
import com.metrodataacademy.domain.entity.Articles;
import com.metrodataacademy.repository.intrf.PaginatedArticlesListIntrf;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ArticlesMapper {
    @Mapping(target = "categories", ignore = true)
    ResArticlesDto paginatedArticlesListIntrfToResArticlesDto(PaginatedArticlesListIntrf paginatedArticlesListIntrf);

    void update(@MappingTarget Articles articles, ReqCreateArticlesDto reqCreateArticlesDto);

    Articles reqCreateArticlesDtoToArticles(ReqCreateArticlesDto reqCreateArticlesDto);
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "author", ignore = true)
    ResDetailArticleDto articleToResDetailArticleDto(Articles articles);
}
