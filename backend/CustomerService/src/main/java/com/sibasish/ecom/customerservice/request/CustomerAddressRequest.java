package com.sibasish.ecom.customerservice.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CustomerAddressRequest {

    private String mobile;
    private Integer houseNo;
    private String addressLine1;
    private String addressLine2;
    private String landmark;
    private Integer pinCode;
    private String city;
    private String country;

    /*
        Unique Identifier
        To be removed after adding security
     */
    private String email;
}
