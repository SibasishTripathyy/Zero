package com.sibasish.ecom.orderservice.response;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponse {

    private UUID orderId;
    private Double totalPrice;
}
