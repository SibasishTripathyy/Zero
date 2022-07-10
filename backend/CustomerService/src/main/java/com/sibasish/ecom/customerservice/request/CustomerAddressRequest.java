package com.sibasish.ecom.customerservice.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class CustomerAddressRequest {

    private String mobile;

    @Min(value = 1, message = "House number is required and min length is 1.")
    private Integer houseNo;

    @NotBlank(message = "Address Line 1 is required.")
    private String addressLine1;
    private String addressLine2;

    @NotBlank(message = "Landmark is required")
    private String landmark;

    @Min(value = 6, message = "Pin Code is required and min length is 6")
    private Integer pinCode;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Country is required")
    private String country;

    /*
        Unique Identifier
        To be removed after adding security
     */
    @NotBlank(message = "Email is required - As of now!")
    private String email;
}
