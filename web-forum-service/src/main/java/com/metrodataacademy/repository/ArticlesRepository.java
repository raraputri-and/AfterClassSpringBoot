package com.metrodataacademy.repository;

import com.metrodataacademy.domain.entity.Articles;
import com.metrodataacademy.repository.intrf.PaginatedArticlesListIntrf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticlesRepository extends JpaRepository<Articles, String> {
    @Query(value =
            "SELECT A.id, A.title, A.image_url, A.body, A.created_at AS createdAt " +
                    "FROM tb_m_articles A JOIN tb_tr_articles_categories AC ON A.id = AC.articles_id JOIN " +
                    "tb_m_categories C ON AC.categories_id = C.id WHERE A.title LIKE %:title% AND C.name " +
                    "LIKE %:category%", nativeQuery = true)
    Page<PaginatedArticlesListIntrf> findAllArticles(
            @Param("title") String title,
            @Param("category") String category,
            Pageable pageable
    );

    Optional<Articles> findById(String id);
}
