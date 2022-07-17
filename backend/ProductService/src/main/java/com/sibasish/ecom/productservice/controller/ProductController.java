package com.sibasish.ecom.productservice.controller;

import com.sibasish.ecom.productservice.request.ProductRequest;
import com.sibasish.ecom.productservice.response.ProductResponse;
import com.sibasish.ecom.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest) {

        ProductResponse productResponse = productService.addProduct(productRequest);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("productId") UUID productId) {

        ProductResponse productResponse = productService.getProductById(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/all/{page}/{pageSize}")
    public ResponseEntity<List<ProductResponse>> getAllProductsPaginated(@PathVariable("page") Integer page,
                                                        @PathVariable("pageSize") Integer pageSize) {

        List<ProductResponse> productResponseList = productService.getAllProductsPaginated(page, pageSize);

        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }
}
