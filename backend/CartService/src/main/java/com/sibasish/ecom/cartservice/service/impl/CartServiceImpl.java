package com.sibasish.ecom.cartservice.service.impl;

import com.sibasish.ecom.cartservice.entity.Cart;
import com.sibasish.ecom.cartservice.exceptions.NoDataFoundException;
import com.sibasish.ecom.cartservice.repository.CartRepository;
import com.sibasish.ecom.cartservice.response.CartResponse;
import com.sibasish.ecom.cartservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Override
    public String addToCart(UUID customerId, UUID productId, Integer quantity) {

        Cart cart = Cart.builder()
                .customerId(customerId)
                .productId(productId)
                .quantity(quantity)
                .build();

        cartRepository.save(cart);

        return "Added to cart successfully.";
    }

    @Override
    public CartResponse getCartItems() {

        List<Cart> cartList = cartRepository.findAll();

        if (cartList.isEmpty()) {
            throw new NoDataFoundException("No items present in cart");
        }

        CartResponse cartResponse = new CartResponse();
        cartResponse.setProductResponseList(new ArrayList<>());

        cartList.forEach(cartItem -> {
            System.out.println("Product ID: " + cartItem.getProductId().toString());
            cartResponse.getProductResponseList().add(cartRepository.findProductById(cartItem.getProductId().toString()));
        });

        return cartResponse;
    }
}
