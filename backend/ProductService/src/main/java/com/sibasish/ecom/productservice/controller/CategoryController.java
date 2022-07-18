package com.sibasish.ecom.productservice.controller;

import com.sibasish.ecom.productservice.request.CategoryRequest;
import com.sibasish.ecom.productservice.response.CategoryResponse;
import com.sibasish.ecom.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {

        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);

        return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {

        List<CategoryResponse> categoryResponseList = categoryService.getAllCategories();

        return new ResponseEntity<>(categoryResponseList, HttpStatus.OK);
    }
}
