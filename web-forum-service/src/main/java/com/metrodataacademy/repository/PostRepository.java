package com.metrodataacademy.repository;

import com.metrodataacademy.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, String> {
    Page<Post> findByThreads_Id(String threadsId, Pageable pageable);
}
