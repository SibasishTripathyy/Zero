package com.sibasish.ecom.productservice.service;

import com.sibasish.ecom.productservice.request.ProductRequest;
import com.sibasish.ecom.productservice.response.ProductResponse;

public interface ProductService {
    ProductResponse addProduct(ProductRequest productRequest);
}
