package com.sibasish.ecom.customerservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CustomerRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String mobile;
}
