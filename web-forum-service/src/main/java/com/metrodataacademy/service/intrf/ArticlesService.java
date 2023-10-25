package com.metrodataacademy.service.intrf;


import com.metrodataacademy.domain.dto.AuthorizationDto;

import com.metrodataacademy.domain.dto.request.ReqCreateArticlesDto;
import com.metrodataacademy.domain.dto.request.ReqGetArticlesDto;
import com.metrodataacademy.domain.dto.response.ResBaseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface ArticlesService {

    ResponseEntity<ResBaseDto> insertArticle(ReqCreateArticlesDto reqCreateArticlesDto, HttpServletRequest servletRequest);

    ResponseEntity<ResBaseDto> getArticlesById(String id);

    ResponseEntity<ResBaseDto> deleteArticle(String id);
    ResponseEntity<ResBaseDto> getAllArticleList(ReqGetArticlesDto requestDetail);
    ResponseEntity<ResBaseDto> updateArticle(String id, ReqCreateArticlesDto reqCreateArticlesDto, AuthorizationDto authDto);
}
