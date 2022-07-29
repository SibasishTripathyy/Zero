package com.sibasish.ecom.cartservice.controller;

import com.sibasish.ecom.cartservice.response.CartResponse;
import com.sibasish.ecom.cartservice.response.OrderResponse;
import com.sibasish.ecom.cartservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam UUID customerId,
                                                  @RequestParam UUID productId,
                                                  @RequestParam Integer quantity) {

        String response = cartService.addToCart(customerId, productId, quantity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<CartResponse> getCartItems() {

        CartResponse cartResponse = cartService.getCartItems();

        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    @PostMapping("/purchase/{customerId}/{addressId}")
    public ResponseEntity<OrderResponse> purchase(@PathVariable("customerId") UUID customerId,
                                                  @PathVariable("addressId") Integer addressId,
                                                  @RequestParam String paymentMethod) {

        OrderResponse orderResponse = cartService.purchase(customerId, addressId, paymentMethod);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
}
