package com.sibasish.ecom.productservice.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    private String productName;
    private String description;
    private Double price;
    private Integer unitsLeft;
    private Boolean outOfStock;
    private Boolean isActive;

    private List<CategoryRequest> categoryRequestList;
}
