package com.sibasish.ecom.orderservice.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class OrderItemRequest {

    private String itemName;
    private Double itemPrice;
    private Integer quantity;

    private UUID productId;
}
