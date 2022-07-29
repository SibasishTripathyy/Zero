package com.sibasish.ecom.cartservice.service.impl;

import com.sibasish.ecom.cartservice.entity.Cart;
import com.sibasish.ecom.cartservice.exceptions.NoDataFoundException;
import com.sibasish.ecom.cartservice.repository.CartRepository;
import com.sibasish.ecom.cartservice.request.OrderItemRequest;
import com.sibasish.ecom.cartservice.request.OrderRequest;
import com.sibasish.ecom.cartservice.response.CartResponse;
import com.sibasish.ecom.cartservice.response.OrderResponse;
import com.sibasish.ecom.cartservice.response.ProductAndQuantityForCustomerResponse;
import com.sibasish.ecom.cartservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RestTemplate restTemplate;

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

    @Override
    @Transactional
    public OrderResponse purchase(UUID customerId, Integer addressId, String paymentMethod) {

        List<ProductAndQuantityForCustomerResponse> productAndQuantityList =
                cartRepository.findProductAndQuantityForCustomer(customerId.toString());

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setPaymentMethod(paymentMethod);
        orderRequest.setAddressId(addressId);


        List<OrderItemRequest> orderItemRequestList = new ArrayList<>();
        productAndQuantityList.forEach(productAndQuantity ->
            orderItemRequestList.add(OrderItemRequest.builder()
                    .productId(productAndQuantity.getProductId())
                    .quantity(productAndQuantity.getQuantity())
                    .build()
            )
        );

        orderRequest.setOrderItemRequestList(orderItemRequestList);

        // Rest Call to Order Service
        OrderResponse orderResponse =
                restTemplate.postForObject("http://localhost:8081/order/create/" + customerId,
                        orderRequest,
                        OrderResponse.class
                );

        if (orderResponse == null) {
            throw new RuntimeException("Problem with rest call from cart to order service. " +
                    "Couldn't fetch purchase details.");
        }

        // Delete items from cart
        cartRepository.deleteByCustomerId(customerId);

        return orderResponse;
    }
}
