package com.sibasish.ecom.orderservice.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private UUID productId;
    private String productName;
    private String description;
    private Double price;
    private Integer unitsLeft;
    private Boolean outOfStock;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
