package com.sibasish.ecom.paymentservice.service.impl;

import com.sibasish.ecom.paymentservice.entity.PaymentDetails;
import com.sibasish.ecom.paymentservice.repository.PaymentRepository;
import com.sibasish.ecom.paymentservice.request.PaymentRequest;
import com.sibasish.ecom.paymentservice.response.PaymentResponse;
import com.sibasish.ecom.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    public PaymentResponse makePayment(PaymentRequest paymentRequest) {

        PaymentDetails paymentDetails = PaymentDetails.builder()
                .paymentStatus("SUCCESS")
                .transactionId(UUID.randomUUID())
                .paymentType(paymentRequest.getPaymentType())
                .amount(paymentRequest.getAmount())
                .orderId(paymentRequest.getOrderId())
                .build();

        paymentDetails = paymentRepository.save(paymentDetails);

        return PaymentResponse.builder()
                .paymentId(paymentDetails.getPaymentId())
                .paymentStatus(paymentDetails.getPaymentStatus())
                .transactionId(paymentDetails.getTransactionId())
                .paymentType(paymentDetails.getPaymentType())
                .amount(paymentDetails.getAmount())
                .orderId(paymentDetails.getOrderId())
                .build();
    }
}
