package com.metrodataacademy.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_tr_articles_categories")
public class ArticlesCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Categories categories;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "articles_id")
    private Articles articles;
}
