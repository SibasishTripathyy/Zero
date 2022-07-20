package com.sibasish.ecom.paymentservice.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long paymentId;
    private String paymentStatus;
    private UUID transactionId;
    private String paymentType;

    private Double amount;
    private UUID orderId;
}
