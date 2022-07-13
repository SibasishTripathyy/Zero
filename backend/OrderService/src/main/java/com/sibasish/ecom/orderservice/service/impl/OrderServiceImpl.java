package com.sibasish.ecom.orderservice.service.impl;

import com.sibasish.ecom.orderservice.entity.Order;
import com.sibasish.ecom.orderservice.entity.OrderItem;
import com.sibasish.ecom.orderservice.repository.CustomerRepository;
import com.sibasish.ecom.orderservice.request.OrderItemRequest;
import com.sibasish.ecom.orderservice.request.OrderRequest;
import com.sibasish.ecom.orderservice.response.OrderResponse;
import com.sibasish.ecom.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest, UUID customerId) {

        double totalPrice = 0.0;
        OrderItem orderItem = new OrderItem();
        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderItemRequest orderItemRequest: orderRequest.getOrderItemRequestList()) {

            totalPrice += orderItemRequest.getItemPrice() * orderItemRequest.getQuantity();
            orderItem.setItemName(orderItemRequest.getItemName());
            orderItem.setItemPrice(orderItemRequest.getItemPrice());
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setOrderTimestamp(LocalDateTime.now());
            orderItemList.add(orderItem);

        }

        try {
            Order order = Order.builder()
                    .orderDate(LocalDate.now())
                    .totalPrice(totalPrice)
                    .paymentMethod(orderRequest.getPaymentMethod())
                    .shippingAddress(orderRequest.getShippingAddress())  // ToDo: Get actual address
                    .orderItemList(orderItemList)
                    .customerId(customerRepository.findById(customerId).orElseThrow(
                            () -> new Exception("as")
                    ))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new OrderResponse();
    }
}
