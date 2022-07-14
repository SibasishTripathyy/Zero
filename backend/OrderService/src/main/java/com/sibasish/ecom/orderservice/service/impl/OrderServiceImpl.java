package com.sibasish.ecom.orderservice.service.impl;

import com.sibasish.ecom.orderservice.entity.Order;
import com.sibasish.ecom.orderservice.entity.OrderItem;
import com.sibasish.ecom.orderservice.exceptions.ResourceNotFoundException;
import com.sibasish.ecom.orderservice.repository.CustomerRepository;
import com.sibasish.ecom.orderservice.repository.OrderRepository;
import com.sibasish.ecom.orderservice.request.OrderItemRequest;
import com.sibasish.ecom.orderservice.request.OrderRequest;
import com.sibasish.ecom.orderservice.response.OrderItemResponse;
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
        List<OrderItemRequest> orderItemRequestList = orderRequest.getOrderItemRequestList();

        for (OrderItemRequest orderItemRequest: orderItemRequestList) {

            totalPrice += orderItemRequest.getItemPrice() * orderItemRequest.getQuantity();

            System.out.println("Just Before Adding to list -> " + orderItem.getItemName());
            orderItemList.add(
                    OrderItem.builder()
                            .itemName(orderItemRequest.getItemName())
                            .itemPrice(orderItemRequest.getItemPrice())
                            .quantity(orderItemRequest.getQuantity())
                            .orderTimestamp(LocalDateTime.now())
                            .build()
            );

            System.out.println("Just After Adding to list -> " + orderItem.getItemName());

            // Just for testing
            System.out.println("Loop Testing");
            for (OrderItem testItem: orderItemList) {
                System.out.println(testItem.getItemName());
            }

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

            List<OrderItemResponse> orderItemResponseList = new ArrayList<>();
            List<OrderItem> savedOrderItemList = order.getOrderItemList();

            for (OrderItem savedOrderItem: savedOrderItemList) {
                System.out.println("savedOrderItem: " + savedOrderItem.getItemName());
                orderItemResponseList.add(
                        OrderItemResponse.builder()
                                .itemName(savedOrderItem.getItemName())
                                .itemPrice(savedOrderItem.getItemPrice())
                                .quantity(savedOrderItem.getQuantity())
                                .build()
                );
            }

            System.out.println("orderItemResponseList" + orderItemResponseList);
            return OrderResponse.builder()
                    .orderId(order.getOrderId())
                    .totalPrice(order.getTotalPrice())
                    .shippingAddress(order.getShippingAddress())
                    .paymentMethod(order.getPaymentMethod())
                    .orderDate(order.getOrderDate())
                    .orderItemResponseList(orderItemResponseList)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
