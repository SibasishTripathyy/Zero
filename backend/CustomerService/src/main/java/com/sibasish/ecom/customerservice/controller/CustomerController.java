package com.sibasish.ecom.customerservice.controller;

import com.sibasish.ecom.customerservice.request.CustomerRequest;
import com.sibasish.ecom.customerservice.response.CustomerResponse;
import com.sibasish.ecom.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/hello")
    public String hello() {
        return "Hi";
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> createUser(@RequestBody CustomerRequest customerRequest) {

        CustomerResponse customerResponse = customerService.createUser(customerRequest);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

}
