package com.sibasish.ecom.cartservice.service;

import com.sibasish.ecom.cartservice.response.CartResponse;

import java.util.UUID;

public interface CartService {
    String addToCart(UUID customerId, UUID productId, Integer quantity);

    CartResponse getCartItems();
}
