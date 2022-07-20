package com.sibasish.ecom.paymentservice.controller;

import com.sibasish.ecom.paymentservice.request.PaymentRequest;
import com.sibasish.ecom.paymentservice.response.PaymentResponse;
import com.sibasish.ecom.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/make")
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody PaymentRequest paymentRequest) {

        PaymentResponse paymentResponse = paymentService.makePayment(paymentRequest);

        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}
