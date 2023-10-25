package com.metrodataacademy.service.impl;

import com.metrodataacademy.domain.constant.ConstantVariable;
import com.metrodataacademy.domain.dto.AuthorizationDto;
import com.metrodataacademy.domain.dto.request.ReqCreateArticlesDto;
import com.metrodataacademy.domain.dto.request.ReqGetArticlesDto;
import com.metrodataacademy.domain.dto.response.*;
import com.metrodataacademy.domain.entity.Articles;
import com.metrodataacademy.domain.entity.ArticlesCategories;
import com.metrodataacademy.domain.entity.Categories;
import com.metrodataacademy.domain.entity.StagingUser;
import com.metrodataacademy.domain.mapper.ArticlesMapper;
import com.metrodataacademy.domain.mapper.CategoriesMapper;
import com.metrodataacademy.domain.mapper.StagingUserMapper;
import com.metrodataacademy.repository.ArticlesCategoriesRepository;
import com.metrodataacademy.repository.ArticlesRepository;
import com.metrodataacademy.repository.CategoriesRepository;
import com.metrodataacademy.repository.StagingUserRepository;
import com.metrodataacademy.repository.intrf.PaginatedArticlesListIntrf;
import com.metrodataacademy.service.intrf.ArticlesService;
import com.metrodataacademy.service.intrf.CategoriesService;
import com.metrodataacademy.util.SlugUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ArticlesServiceImpl implements ArticlesService {
    private final ArticlesRepository articlesRepository;
    private final ArticlesCategoriesRepository articlesCategoriesRepository;
    private final CategoriesRepository categoriesRepository;
    private final CategoriesService categoriesService;
    private final CategoriesMapper categoriesMapper;
    private final ArticlesMapper articlesMapper;
    private final StagingUserMapper stagingUserMapper;
    private final StagingUserRepository stagingUserRepository;

    @Override
    public ResponseEntity<ResBaseDto> insertArticle(ReqCreateArticlesDto reqCreateArticlesDto, HttpServletRequest servletRequest) {
        try {

            AuthorizationDto authDto = (AuthorizationDto) servletRequest.getAttribute(ConstantVariable.USER);

            Articles articles = articlesMapper.reqCreateArticlesDtoToArticles(reqCreateArticlesDto);
            articles.setSlug(SlugUtil.toSlug(reqCreateArticlesDto.getTitle()));
            articles.setCounter(0);
            articles.setPublish(true);
            articles.setActive(true);

            Optional<StagingUser> optStagingUser = stagingUserRepository.findById(authDto.getId());
            StagingUser stagingUser = new StagingUser();
            if (optStagingUser.isEmpty()) {
                stagingUser.setId(authDto.getId());
                stagingUser.setName(authDto.getName());
                stagingUser = stagingUserRepository.save(stagingUser);
            } else {
                stagingUser = optStagingUser.get();
            }

            articles.setAuthor(stagingUser);
            articlesRepository.save(articles);

            if (reqCreateArticlesDto.getCategories() != null){
                reqCreateArticlesDto.getCategories().forEach(data -> {
                    ArticlesCategories articlesCategories = new ArticlesCategories();
                    articlesCategories.setCategories(categoriesService.getCategoriesById(data.getId()));
                    articlesCategories.setArticles(articles);
                    articlesCategoriesRepository.save(articlesCategories);
                });
            }

            ResArticlesDto resArticleDto = new ResArticlesDto();
            resArticleDto.setId(articles.getId());

            return new ResponseEntity<>(new ResBaseDto(resArticleDto, ConstantVariable.SUCCESS), HttpStatus.OK);

        } catch (ResponseStatusException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @Override
    public ResponseEntity<ResBaseDto> getArticlesById(String id) {
        Articles articles = articlesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Articles ID is not found!!!"));

        ResDetailArticleDto resDetailArticleDto = articlesMapper.articleToResDetailArticleDto(articles);

        List<ResCategoriesDto> categoriesDtoList = new ArrayList<>();

        List<ArticlesCategories> articlesCategories = articlesCategoriesRepository.findByArticles_Id(id);
        articlesCategories.forEach(
                listCategory -> {
                    Categories categories = listCategory.getCategories();
                    ResCategoriesDto resCategoriesDto = categoriesMapper.categoriesToResCategoriesDto(categories);
                    categoriesDtoList.add(resCategoriesDto);
                }
        );
        resDetailArticleDto.setCategories(categoriesDtoList);

        ResStagingUserDto resStagingUserDto = stagingUserMapper.stagingUserToResStagingUserDto(articles.getAuthor());
        resDetailArticleDto.setAuthor(resStagingUserDto);

        return new ResponseEntity<>(new ResBaseDto(resDetailArticleDto, ConstantVariable.SUCCESS), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResBaseDto> deleteArticle(String id) {
        try {
            articlesRepository.deleteById(id);
            return new ResponseEntity<>(new ResBaseDto(null,ConstantVariable.SUCCESS), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @Override
    public ResponseEntity<ResBaseDto> getAllArticleList(ReqGetArticlesDto requestDetail) {
        Pageable pageable = PageRequest.of(requestDetail.getPage() -1,
                ConstantVariable.DATA_PER_PAGE);
        Page<PaginatedArticlesListIntrf> pageResult = articlesRepository.findAllArticles(requestDetail.getTitle(), requestDetail.getCategory(), pageable);

        List<ResArticlesDto> responseList = new ArrayList<>();
        pageResult.forEach(
                response -> {
                    ResArticlesDto articlesResponse = articlesMapper.paginatedArticlesListIntrfToResArticlesDto(response);

                    List<ResCategoriesDto> categoriesDtoList = new ArrayList<>();

                    List<ArticlesCategories> articlesCategories = articlesCategoriesRepository.findByArticles_Id(response.getId());
                    articlesCategories.forEach(
                            listCategory -> {
                                Categories categories = listCategory.getCategories();
                                ResCategoriesDto resCategoriesDto = categoriesMapper.categoriesToResCategoriesDto(categories);
                                categoriesDtoList.add(resCategoriesDto);
                            }
                    );
                    articlesResponse.setCategories(categoriesDtoList);
                    responseList.add(articlesResponse);
                }
        );
        return ResponseEntity.ok(new ResBaseDto(responseList, ConstantVariable.SUCCESS));
    }

    @Override
    public ResponseEntity<ResBaseDto> updateArticle(String id, ReqCreateArticlesDto reqCreateArticlesDto, AuthorizationDto authDto) {
        try {

            Articles articlesById = articlesRepository.findById(id).get();
            articlesMapper.update(articlesById, reqCreateArticlesDto);
            articlesRepository.save(articlesById);

            if (reqCreateArticlesDto.getCategories() != null){
                List<ArticlesCategories> articlesCategoriesList = articlesById.getArticlesCategories();
                if (articlesCategoriesList.size() != 0){

                    Map<String, String> articlesCategoriesMap = new HashMap<>();
                    articlesCategoriesList.forEach(articlesCategories -> {
                        articlesCategoriesMap.put(articlesCategories.getCategories().getId(), articlesCategories.getId());
                    });

                    reqCreateArticlesDto.getCategories().forEach(categories -> {
                        if (articlesCategoriesMap.containsKey(categories.getId())){
                            articlesCategoriesMap.put(categories.getId(), "save");
                        } else {
                            ArticlesCategories articlesCategories = new ArticlesCategories();
                            articlesCategories.setCategories(categoriesService.getCategoriesById(categories.getId()));
                            articlesCategories.setArticles(articlesById);
                            articlesCategoriesRepository.save(articlesCategories);
                        }
                    });

                    for (Map.Entry<String, String> keyValueSet : articlesCategoriesMap.entrySet()){
                        if (!keyValueSet.getValue().equals("save")) {
                            articlesCategoriesRepository.deleteById(keyValueSet.getValue());
                        }
                    }
                } else {
                    reqCreateArticlesDto.getCategories().forEach(data -> {
                        ArticlesCategories articlesCategories = new ArticlesCategories();
                        articlesCategories.setCategories(categoriesService.getCategoriesById(data.getId()));
                        articlesCategories.setArticles(articlesById);
                        articlesCategoriesRepository.save(articlesCategories);
                    });
                }
            }

            ResArticlesDto resArticlesDto = new ResArticlesDto();
            resArticlesDto.setId(articlesById.getId());

            return new ResponseEntity<>(new ResBaseDto(resArticlesDto, ConstantVariable.SUCCESS), HttpStatus.OK);

        } catch (ResponseStatusException e){
            throw new ResponseStatusException(e.getStatusCode(),e.getReason());
        }
    }
}
