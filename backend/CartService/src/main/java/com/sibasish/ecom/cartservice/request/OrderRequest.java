package com.sibasish.ecom.cartservice.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class OrderRequest {

    private String paymentMethod;
    private Integer addressId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<OrderItemRequest> orderItemRequestList;

    private PaymentRequest paymentRequest;
}
