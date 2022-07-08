package com.sibasish.ecom.customerservice.controller;

import com.sibasish.ecom.customerservice.request.CustomerRequest;
import com.sibasish.ecom.customerservice.response.CustomerResponse;
import com.sibasish.ecom.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/hello")
    public String hello() {
        return "Hi";
    }

    /*
    public ResponseEntity<CustomerResponse> createUser(@RequestBody CustomerRequest customerRequest) {

        return new ResponseEntity<CustomerResponse>(HttpStatus.OK);
    }
     */

}
