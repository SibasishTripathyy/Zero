package com.sibasish.ecom.orderservice.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponse {

    private UUID orderId;
    private Double totalPrice;
    private String shippingAddress;
    private String paymentMethod;
    private LocalDate orderDate;

    private List<OrderItemResponse> orderItemResponseList;

    private PaymentResponse paymentResponse;
}
