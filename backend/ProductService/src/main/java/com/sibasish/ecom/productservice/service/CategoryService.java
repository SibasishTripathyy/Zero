package com.sibasish.ecom.productservice.service;

import com.sibasish.ecom.productservice.response.CategoryResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();
}
