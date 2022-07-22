package com.sibasish.ecom.cartservice.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {

    private List<ProductResponse> productResponseList;
}
