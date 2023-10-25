package com.metrodataacademy.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_m_categories")
public class Categories extends BaseEntity{

    @Column(unique = true, length = 50, nullable = false)
    private String name;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "tb_tr_article_categories", joinColumns = @JoinColumn(name = "categories_id"),
//            inverseJoinColumns = @JoinColumn(name = "articles_id"))
//    private List<Articles> articles;

    @OneToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    private List<ArticlesCategories> articlesCategories;
}
