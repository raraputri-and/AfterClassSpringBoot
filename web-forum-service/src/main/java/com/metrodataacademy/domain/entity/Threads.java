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
@Table(name = "tb_m_threads")
@SQLDelete(sql = "UPDATE tb_m_threads SET is_active = false WHERE id=?")
@Where(clause = "is_active = true")
public class Threads extends BaseEntity{
    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 50, nullable = false)
    private String slug;

    @Column(nullable = false)
    private Integer counter;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "staging_user_id")
    private StagingUser author;

    @JsonIgnore
    @ManyToMany(mappedBy = "likedThreads")
    private List<StagingUser> likedBy;

    @OneToMany(mappedBy = "threads", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Post> posts;
}
