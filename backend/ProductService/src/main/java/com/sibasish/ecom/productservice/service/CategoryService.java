package com.sibasish.ecom.productservice.service;

import com.sibasish.ecom.productservice.request.CategoryRequest;
import com.sibasish.ecom.productservice.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest categoryRequest);
    List<CategoryResponse> getAllCategories();
}
