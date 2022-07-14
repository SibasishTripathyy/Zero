package com.sibasish.ecom.orderservice.service.impl;

import com.sibasish.ecom.orderservice.entity.Order;
import com.sibasish.ecom.orderservice.entity.OrderItem;
import com.sibasish.ecom.orderservice.exceptions.ResourceNotFoundException;
import com.sibasish.ecom.orderservice.repository.CustomerRepository;
import com.sibasish.ecom.orderservice.repository.OrderRepository;
import com.sibasish.ecom.orderservice.request.OrderItemRequest;
import com.sibasish.ecom.orderservice.request.OrderRequest;
import com.sibasish.ecom.orderservice.response.OrderResponse;
import com.sibasish.ecom.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

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
                    .shippingAddress(orderRequest.getShippingAddress())  // ToDo: Get actual address
                    .paymentMethod(orderRequest.getPaymentMethod())
                    .orderItemList(orderItemList)
                    .customerId(customerRepository.findById(customerId).orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Customer with id -> " + customerId + " not found"
                            )
                    ))
                    .orderItemList(orderItemList)
                    .build();

            order = orderRepository.save(order);

            return OrderResponse.builder()
                    .orderId(order.getOrderId())
                    .totalPrice(order.getTotalPrice())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
