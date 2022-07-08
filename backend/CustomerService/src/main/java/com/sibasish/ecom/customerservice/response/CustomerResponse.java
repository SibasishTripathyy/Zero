package com.sibasish.ecom.customerservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerResponse {

    private UUID customerId;
    private String firstName;
    private String lastName;
    private String email;
    private Integer mobile;
}
