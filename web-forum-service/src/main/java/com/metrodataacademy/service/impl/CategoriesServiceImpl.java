package com.metrodataacademy.service.impl;

import com.metrodataacademy.domain.entity.Categories;
import com.metrodataacademy.repository.CategoriesRepository;
import com.metrodataacademy.service.intrf.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {
    private final CategoriesRepository categoriesRepository;

    @Override
    public Categories getCategoriesById(String id) {
        return categoriesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category ID: " + id + " NOT FOUND"));
    }
}
