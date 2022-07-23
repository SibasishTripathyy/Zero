package com.sibasish.ecom.orderservice.service.impl;

import com.sibasish.ecom.orderservice.entity.Order;
import com.sibasish.ecom.orderservice.entity.OrderItem;
import com.sibasish.ecom.orderservice.exceptions.InsufficientQuantityException;
import com.sibasish.ecom.orderservice.exceptions.NoDataFoundException;
import com.sibasish.ecom.orderservice.exceptions.ResourceNotFoundException;
import com.sibasish.ecom.orderservice.repository.OrderRepository;
import com.sibasish.ecom.orderservice.request.OrderItemRequest;
import com.sibasish.ecom.orderservice.request.OrderRequest;
import com.sibasish.ecom.orderservice.request.PaymentRequest;
import com.sibasish.ecom.orderservice.response.*;
import com.sibasish.ecom.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.naming.InsufficientResourcesException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    private String shippingAddressBuilderHelper(CustomerAddressResponse customerAddressResponse,
                                                Boolean isSecondaryAddressPresent) {

        if (isSecondaryAddressPresent) {

            return customerAddressResponse.getHouseNo() + ", "
                    + customerAddressResponse.getAddressLine1() + ", "
                    + customerAddressResponse.getAddressLine2() + ", "
                    + customerAddressResponse.getLandmark() + ", "
                    + customerAddressResponse.getCity() + ", "
                    + customerAddressResponse.getCountry();
        } else {

            return customerAddressResponse.getHouseNo() + ", "
                    + customerAddressResponse.getAddressLine1() + ", "
                    + customerAddressResponse.getLandmark() + ", "
                    + customerAddressResponse.getCity() + ", "
                    + customerAddressResponse.getCountry();
        }
    }
    private String shippingAddressBuilder(CustomerAddressResponse customerAddressResponse) {

        return shippingAddressBuilderHelper(customerAddressResponse,
                customerAddressResponse.getAddressLine2() != null
                );
    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest, UUID customerId) {

        double totalPrice = 0.0;

        List<OrderItem> orderItemList = new ArrayList<>();
        List<OrderItemRequest> orderItemRequestList = orderRequest.getOrderItemRequestList();

        for (OrderItemRequest orderItemRequest: orderItemRequestList) {

            UUID productId = orderItemRequest.getProductId();
            Integer quantity = orderItemRequest.getQuantity();

            // Rest Call to product service for product details
            ProductResponse productResponse = restTemplate.getForObject(
                    "http://localhost:8082/product/" + productId,
                    ProductResponse.class
            );

            assert productResponse != null;

            // Check if units left >= quantity requested
            if (productResponse.getOutOfStock() || productResponse.getUnitsLeft() < quantity) {
                throw new InsufficientQuantityException("Not enough units are available for sale.",
                        productResponse.getProductName(), productResponse.getUnitsLeft(), quantity);
            }

            totalPrice += productResponse.getPrice() * quantity;

            orderItemList.add(
                    OrderItem.builder()
                            .itemName(productResponse.getProductName())
                            .itemPrice(productResponse.getPrice())
                            .quantity(quantity)
                            .build()
            );

            Integer rowsAffected = orderRepository.updateProductQuantity(productId.toString(), quantity);
            System.out.println("Rows Affected: " + rowsAffected);
        }

        /* Rest call to customer service for customer details
        *
        * 1. Validate the customer ID.
        * 2. Fetch the customer address
        */
        ResponseEntity<CustomerResponse> customerResponse =
                restTemplate.getForEntity("http://localhost:8080/customer/" + customerId, CustomerResponse.class);

        if (customerResponse.getStatusCode().value() != 200) {
            throw new RuntimeException("Problem with rest call from order to customer service. " +
                    "Couldn't fetch customer details");
        }

        ResponseEntity<CustomerAddressResponse> customerAddressResponse =
                restTemplate.getForEntity("http://localhost:8080/customer/address/" + orderRequest.getAddressId(),
                        CustomerAddressResponse.class);

        if (customerAddressResponse.getStatusCode().value() != 200) {
            throw new RuntimeException("Problem with rest call from order to customer service. " +
                    "Couldn't Fetch Address Details");
        }

        try {
            Order order = Order.builder()
                    .orderDate(LocalDate.now())
                    .totalPrice(totalPrice)
                    .shippingAddress(shippingAddressBuilder(customerAddressResponse.getBody()))
                    .paymentMethod(orderRequest.getPaymentMethod())
                    .orderItemList(orderItemList)
                    .customerId(customerId)
                    .orderItemList(orderItemList)
                    .build();

            order = orderRepository.save(order);

            // Rest Call To Payment Service
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setOrderId(order.getOrderId());
            paymentRequest.setAmount(order.getTotalPrice());
            paymentRequest.setPaymentType(order.getPaymentMethod());

            PaymentResponse paymentResponse =
                    restTemplate.postForObject("http://localhost:8083/payment/make",
                            paymentRequest,
                            PaymentResponse.class
                    );

            if (paymentResponse == null) {
                throw new RuntimeException("Problem with rest call from order to payment service. " +
                        "Couldn't fetch payment details.");
            }

            if (paymentResponse.getPaymentStatus().equals("SUCCESS")) {
                paymentResponse.setMessage("Payment received");
            } else {
                paymentResponse.setMessage("Payment Failed");
            }

            // Order Response
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
                    .paymentResponse(paymentResponse)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
