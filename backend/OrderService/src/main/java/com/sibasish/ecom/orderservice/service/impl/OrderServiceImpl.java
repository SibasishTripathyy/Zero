package com.sibasish.ecom.orderservice.service.impl;

import com.sibasish.ecom.orderservice.entity.CustomerAddress;
import com.sibasish.ecom.orderservice.entity.Order;
import com.sibasish.ecom.orderservice.entity.OrderItem;
import com.sibasish.ecom.orderservice.exceptions.NoDataFoundException;
import com.sibasish.ecom.orderservice.exceptions.ResourceNotFoundException;
import com.sibasish.ecom.orderservice.repository.CustomerAddressRepository;
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
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    @Autowired
    private OrderRepository orderRepository;

    private String shippingAddressBuilderHelper(CustomerAddress customerAddress,
                                                Boolean isSecondaryAddressPresent) {

        if (isSecondaryAddressPresent) {

            return customerAddress.getHouseNo() + ", "
                    + customerAddress.getAddressLine1() + ", "
                    + customerAddress.getAddressLine2() + ", "
                    + customerAddress.getLandmark() + ", "
                    + customerAddress.getCity() + ", "
                    + customerAddress.getCountry();
        } else {

            return customerAddress.getHouseNo() + ", "
                    + customerAddress.getAddressLine1() + ", "
                    + customerAddress.getLandmark() + ", "
                    + customerAddress.getCity() + ", "
                    + customerAddress.getCountry();
        }
    }
    private String shippingAddressBuilder(Integer addressId) {

        Optional<CustomerAddress> customerAddressOptional = customerAddressRepository.findById(addressId);

        if (customerAddressOptional.isPresent()) {

            CustomerAddress customerAddress = customerAddressOptional.get();

            String shippingAddress = "";

            return shippingAddressBuilderHelper(customerAddress,
                            customerAddress.getAddressLine2() != null
                    );
        }

        throw new NoDataFoundException("No address data found for address id: " + addressId);
    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest, UUID customerId) {

        double totalPrice = 0.0;
        OrderItem orderItem = new OrderItem();
        List<OrderItem> orderItemList = new ArrayList<>();
        List<OrderItemRequest> orderItemRequestList = orderRequest.getOrderItemRequestList();

        for (OrderItemRequest orderItemRequest: orderItemRequestList) {

            totalPrice += orderItemRequest.getItemPrice() * orderItemRequest.getQuantity();

            orderItemList.add(
                    OrderItem.builder()
                            .itemName(orderItemRequest.getItemName())
                            .itemPrice(orderItemRequest.getItemPrice())
                            .quantity(orderItemRequest.getQuantity())
                            .orderTimestamp(LocalDateTime.now())
                            .build()
            );

        }

        try {
            Order order = Order.builder()
                    .orderDate(LocalDate.now())
                    .totalPrice(totalPrice)
                    .shippingAddress(shippingAddressBuilder(orderRequest.getAddressId()))
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

                orderItemResponseList.add(
                        OrderItemResponse.builder()
                                .itemName(savedOrderItem.getItemName())
                                .itemPrice(savedOrderItem.getItemPrice())
                                .quantity(savedOrderItem.getQuantity())
                                .build()
                );
            }

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
