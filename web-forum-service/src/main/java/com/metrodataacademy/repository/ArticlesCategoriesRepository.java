package com.metrodataacademy.repository;

import com.metrodataacademy.domain.entity.ArticlesCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticlesCategoriesRepository extends JpaRepository<ArticlesCategories, String> {
    List<ArticlesCategories> findByArticles_Id(String id);
}
