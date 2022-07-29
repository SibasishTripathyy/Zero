package com.sibasish.ecom.cartservice.response;

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
