package com.sibasish.ecom.orderservice.service;

import com.sibasish.ecom.orderservice.request.OrderRequest;
import com.sibasish.ecom.orderservice.response.OrderResponse;

import javax.naming.InsufficientResourcesException;
import java.util.UUID;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest, UUID customerId);
}
