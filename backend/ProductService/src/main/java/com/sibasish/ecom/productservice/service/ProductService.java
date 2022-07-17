package com.sibasish.ecom.productservice.service;

import com.sibasish.ecom.productservice.request.ProductRequest;
import com.sibasish.ecom.productservice.response.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponse addProduct(ProductRequest productRequest);

    ProductResponse getProductById(UUID productId);

    List<ProductResponse> getAllProductsPaginated(Integer page, Integer pageSize);
}
