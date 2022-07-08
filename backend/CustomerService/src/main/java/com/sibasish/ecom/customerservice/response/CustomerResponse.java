package com.sibasish.ecom.customerservice.response;

import com.sibasish.ecom.customerservice.entity.Role;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerResponse {

    private UUID customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
}
