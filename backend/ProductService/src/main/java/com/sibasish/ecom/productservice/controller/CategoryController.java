package com.sibasish.ecom.productservice.controller;

import com.sibasish.ecom.productservice.response.CategoryResponse;
import com.sibasish.ecom.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {

        List<CategoryResponse> categoryResponseList = categoryService.getAllCategories();

        return new ResponseEntity<>(categoryResponseList, HttpStatus.OK);
    }
}
