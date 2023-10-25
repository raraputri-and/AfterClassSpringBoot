package com.metrodataacademy.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_staging_user")
public class StagingUser {
    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Articles> listArticles;

    @JsonIgnore
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Threads> listThreads;

    @JsonIgnore
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Threads> listPost;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "tb_tr_threads_like",
        joinColumns = @JoinColumn(name="staging_user_id"),
        inverseJoinColumns = @JoinColumn(name="threads_id"))
    private List<Threads> likedThreads;
//
//    @JsonIgnore
//    @ManyToMany
//    @JoinTable(name = "tb_tr_post_like",
//            joinColumns = @JoinColumn(name="staging_user_id"),
//            inverseJoinColumns = @JoinColumn(name="post_id"))
//    private List<Post> likedPost;
}
