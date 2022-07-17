package com.sibasish.ecom.productservice.service.impl;

import com.sibasish.ecom.productservice.entity.Category;
import com.sibasish.ecom.productservice.entity.Product;
import com.sibasish.ecom.productservice.exceptions.ResourceNotFoundException;
import com.sibasish.ecom.productservice.repository.CategoryRepository;
import com.sibasish.ecom.productservice.repository.ProductRepository;
import com.sibasish.ecom.productservice.request.ProductRequest;
import com.sibasish.ecom.productservice.response.CategoryResponse;
import com.sibasish.ecom.productservice.response.ProductResponse;
import com.sibasish.ecom.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;
    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {

        List<Category> categoryList = new ArrayList<>();

        productRequest.getCategoryRequestList().forEach(
                categoryRequest -> {

                    String categoryName = categoryRequest.getCategoryName().toLowerCase(Locale.ROOT);

                    System.out.println(categoryName);

                    if (categoryRepository.findByName(categoryName) != null) {
                        categoryList.add(categoryRepository.findByName(categoryName));
                    } else {

                        Category category = Category.builder()
                                .name(categoryRequest.getCategoryName().toLowerCase(Locale.ROOT))
                                .build();

                        categoryList.add(category);
                    }

                }
        );

        Product product = Product.builder()
                .name(productRequest.getProductName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .unitsLeft(productRequest.getUnitsLeft())
                .outOfStock(productRequest.getOutOfStock())
                .isActive(productRequest.getIsActive())
                .categoryList(categoryList)
                .build();

        Product finalProduct = product;
        categoryList.forEach(category -> {

            if (category.getProductList() == null) {
                category.setProductList(new ArrayList<>());
            }

            category.getProductList().add(finalProduct);
        });

        product = productRepository.save(product);

        List<CategoryResponse> categoryResponseList = new ArrayList<>();

        product.getCategoryList().forEach(
                category ->
                    categoryResponseList.add(CategoryResponse.builder()
                            .categoryName(category.getName())
                            .build()
                    )
        );


        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .unitsLeft(product.getUnitsLeft())
                .outOfStock(product.getOutOfStock())
                .isActive(product.getIsActive())
                .createdAt(product.getCreatedAt())
                .categoryResponseList(categoryResponseList)
                .build();
    }

    @Override
    public ProductResponse getProductById(UUID productId) {

        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {

            Product product = optionalProduct.get();

            List<Category> categoryList = product.getCategoryList();
            List<CategoryResponse> categoryResponseList = new ArrayList<>();

            categoryList.forEach(category ->
                    categoryResponseList.add(CategoryResponse.builder()
                        .categoryName(category.getName())
                        .build()
                )
            );

            return ProductResponse.builder()
                    .productId(product.getProductId())
                    .productName(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .unitsLeft(product.getUnitsLeft())
                    .outOfStock(product.getOutOfStock())
                    .isActive(product.getIsActive())
                    .createdAt(product.getCreatedAt())
                    .categoryResponseList(categoryResponseList)
                    .build();
        }

        throw new ResourceNotFoundException("Product not found for id: " + productId);
    }
}
