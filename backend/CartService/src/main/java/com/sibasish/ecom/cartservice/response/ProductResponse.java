package com.sibasish.ecom.cartservice.response;

import java.util.UUID;

public interface ProductResponse {

    UUID getProductId();
    String getProductName();
    String getDescription();
    Double getPrice();
    Integer getUnitsLeft();
    Boolean getOutOfStock();
}
