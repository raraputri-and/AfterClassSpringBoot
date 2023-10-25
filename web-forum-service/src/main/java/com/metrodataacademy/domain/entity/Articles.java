package com.metrodataacademy.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_m_articles")
@SQLDelete(sql = "UPDATE tb_m_articles SET is_active = false WHERE id=?")
@Where(clause = "is_active = true")
public class Articles extends BaseEntity{

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 50, nullable = false)
    private String slug;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String banner;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String imageUrl;

    @Column(name = "is_publish", nullable = false)
    private boolean isPublish;

    @Column(nullable = false)
    private Integer counter;

//    @ManyToMany(mappedBy = "articles")
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    private List<Categories> categories;

    @OneToMany(mappedBy = "articles", fetch = FetchType.EAGER)
    private List<ArticlesCategories> articlesCategories;

    @ManyToOne
    @JoinColumn(name = "staging_user_id")
    private StagingUser author;
}
