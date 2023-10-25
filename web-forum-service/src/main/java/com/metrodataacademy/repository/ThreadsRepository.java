package com.metrodataacademy.repository;

import com.metrodataacademy.domain.entity.Threads;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ThreadsRepository extends JpaRepository<Threads, String> {
    @Query(
            value = "SELECT * FROM tb_m_threads ORDER BY created_at DESC", nativeQuery = true)
    Page<Threads> findAllThreadsDesc(
            Pageable pageable
    );

    @Query(
            value = "SELECT * FROM tb_m_threads ORDER BY created_at ASC", nativeQuery = true)
    Page<Threads> findAllThreadsAsc(
            Pageable pageable
    );
    @Query(
            value = "SELECT * FROM tb_m_threads WHERE title LIKE %:keyword% ORDER BY created_at DESC", nativeQuery = true)
    Page<Threads> findAllThreadsWithKeywordDesc(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query(
            value = "SELECT * FROM tb_m_threads WHERE title LIKE %:keyword% ORDER BY created_at ASC", nativeQuery = true)
    Page<Threads> findAllThreadsWithKeywordAsc(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query(
            value = "SELECT * FROM tb_m_threads ORDER BY counter ASC", nativeQuery = true)
    Page<Threads> findAllThreadsByTotalViewsAsc(
            Pageable pageable
    );

    @Query(
            value = "SELECT * FROM tb_m_threads ORDER BY counter DESC", nativeQuery = true)
    Page<Threads> findAllThreadsByTotalViewsDesc(
            Pageable pageable
    );

    @Query(
            value = "SELECT t.*, COUNT(p.threads_id) AS totalComment FROM tb_m_threads t LEFT JOIN tb_m_posts p ON t.id = p.threads_id GROUP BY t.id ORDER BY totalComment ASC", nativeQuery = true)
    Page<Threads> findAllThreadsByTotalCommentsAsc(
            Pageable pageable
    );

    @Query(
            value = "SELECT t.*, COUNT(p.threads_id) AS totalComment FROM tb_m_threads t LEFT JOIN tb_m_posts p ON t.id = p.threads_id GROUP BY t.id ORDER BY totalComment DESC", nativeQuery = true)
    Page<Threads> findAllThreadsByTotalCommentsDesc(
            Pageable pageable
    );

    @Query(value = "SELECT COUNT(*) FROM tb_m_posts WHERE threads_id = :threadId", nativeQuery = true)
    long getTotalComments(@Param("threadId") String threadId);
}
