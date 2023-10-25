package com.metrodataacademy.controller;

import com.metrodataacademy.domain.constant.ConstantVariable;
import com.metrodataacademy.domain.dto.AuthorizationDto;

import com.metrodataacademy.domain.dto.request.ReqCreateArticlesDto;
import com.metrodataacademy.domain.dto.request.ReqGetArticlesDto;
import com.metrodataacademy.domain.dto.response.ResBaseDto;
import com.metrodataacademy.service.impl.ArticlesServiceImpl;
import com.metrodataacademy.service.intrf.ArticlesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticlesController {

    private final ArticlesService articlesService;

    @PostMapping("/list")
    public ResponseEntity<?> getArticlesList(@Valid @RequestBody ReqGetArticlesDto articlesRequest){
        return articlesService.getAllArticleList(articlesRequest);
    }

    @GetMapping("")
    public ResponseEntity<ResBaseDto> getArticlesById(@RequestParam String id){
        return articlesService.getArticlesById(id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResBaseDto> deleteArticles(@RequestParam String id){
        return articlesService.deleteArticle(id);
    }

    @PostMapping("/create")
    public ResponseEntity<ResBaseDto> insertArticle(@Valid @RequestBody ReqCreateArticlesDto reqCreateArticlesDto, HttpServletRequest servletRequest) {
        return articlesService.insertArticle(reqCreateArticlesDto, servletRequest);
    }

    @PostMapping("/update")
    public ResponseEntity<ResBaseDto> updateArticle(@RequestParam String id, @Valid @RequestBody ReqCreateArticlesDto reqCreateArticlesDto, HttpServletRequest servletRequest) {
        AuthorizationDto authDto = (AuthorizationDto) servletRequest.getAttribute(ConstantVariable.USER);
        return articlesService.updateArticle(id, reqCreateArticlesDto, authDto);
    }
}
