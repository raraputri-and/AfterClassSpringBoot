package com.metrodataacademy.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "tb_m_post")
@SQLDelete(sql = "UPDATE tb_m_post SET is_active = false WHERE id=?")
@Where(clause = "is_active = true")
public class Post extends BaseEntity{

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "staging_user_id")
    private StagingUser author;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "threads_id")
    private Threads threads;


//    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    private List<PostLike> postLikes = new ArrayList<>();
//
//    @JsonIgnore
//    @ManyToMany(mappedBy = "likedPost")
//    private List<StagingUser> likedBy;
}