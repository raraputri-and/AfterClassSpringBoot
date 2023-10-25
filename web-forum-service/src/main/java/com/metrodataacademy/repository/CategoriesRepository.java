package com.metrodataacademy.repository;

import com.metrodataacademy.domain.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, String> {
    // TODO: Lengkapi query sesuai kebutuhan
}
