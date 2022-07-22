package com.sibasish.ecom.orderservice.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAddressResponse {

    private String mobile;
    private Integer houseNo;
    private String addressLine1;
    private String addressLine2;
    private String landmark;
    private Integer pinCode;
    private String city;
    private String country;
}
