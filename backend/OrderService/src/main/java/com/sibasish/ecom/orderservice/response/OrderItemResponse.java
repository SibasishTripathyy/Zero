package com.sibasish.ecom.orderservice.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderItemResponse {

    private String itemName;
    private Double itemPrice;
    private Integer quantity;
}
