package com.sibasish.ecom.orderservice.controller;

import com.sibasish.ecom.orderservice.request.OrderRequest;
import com.sibasish.ecom.orderservice.response.OrderResponse;
import com.sibasish.ecom.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.InsufficientResourcesException;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    @PostMapping("/create/{customerId}")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest,
                                                     @PathVariable("customerId") UUID customerId) {
        OrderResponse orderResponse = orderService.createOrder(orderRequest, customerId);

        logger.info("Order created successfully.");
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }
}
