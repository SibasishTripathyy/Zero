package com.sibasish.ecom.paymentservice.service;

import com.sibasish.ecom.paymentservice.request.PaymentRequest;
import com.sibasish.ecom.paymentservice.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse makePayment(PaymentRequest paymentRequest);
}
