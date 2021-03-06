package com.sibasish.ecom.customerservice.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {

    private Integer statusCode;
    private String message;
    private LocalDateTime timestamp;
}
