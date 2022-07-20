package com.sibasish.ecom.orderservice.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class OrderRequest {

    private String paymentMethod;
    private Integer addressId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<OrderItemRequest> orderItemRequestList;

    private PaymentRequest paymentRequest;
}
