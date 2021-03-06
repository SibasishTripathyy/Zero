package com.sibasish.ecom.productservice.service.impl;

import com.sibasish.ecom.productservice.entity.Category;
import com.sibasish.ecom.productservice.exceptions.NoDataFoundException;
import com.sibasish.ecom.productservice.repository.CategoryRepository;
import com.sibasish.ecom.productservice.request.CategoryRequest;
import com.sibasish.ecom.productservice.response.CategoryResponse;
import com.sibasish.ecom.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {

        Category category = Category.builder()
                .name(categoryRequest.getCategoryName().toLowerCase(Locale.ROOT))
                .build();

        category = categoryRepository.save(category);

        return CategoryResponse.builder()
                .categoryName(category.getName())
                .build();
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        List<Category> categoryList = categoryRepository.findAll();

        if (categoryList.isEmpty()) {
            throw new NoDataFoundException("No categories found.");
        }

        List<CategoryResponse> categoryResponseList = new ArrayList<>();

        categoryList.forEach(category ->
                categoryResponseList.add(CategoryResponse.builder()
                .categoryName(category.getName())
                .build())
        );

        return categoryResponseList;
    }
}
