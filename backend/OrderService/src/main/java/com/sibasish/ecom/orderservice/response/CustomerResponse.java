package com.sibasish.ecom.orderservice.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;
    private String email;
    private String mobile;
}
