package com.sibasish.ecom.orderservice.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class OrderRequest {

    private Double totalPrice;
    private String shippingAddress;
    private String paymentMethod;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private UUID customerId;
    private List<OrderItemRequest> orderItemRequestList;
}
