package com.sibasish.ecom.customerservice.controller;

import com.sibasish.ecom.customerservice.request.CustomerAddressRequest;
import com.sibasish.ecom.customerservice.request.CustomerRequest;
import com.sibasish.ecom.customerservice.response.CustomerAddressResponse;
import com.sibasish.ecom.customerservice.response.CustomerResponse;
import com.sibasish.ecom.customerservice.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> createUser(@RequestBody @Valid CustomerRequest customerRequest) {

        CustomerResponse customerResponse = customerService.createUser(customerRequest);

        logger.info("Customer with email " + customerResponse.getEmail() + " was created");
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("customerId") UUID customerId) {

        CustomerResponse customerResponse = customerService.getCustomerById(customerId);

        logger.info("Customer with email " + customerResponse.getEmail() + " was fetched");
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {

        List<CustomerResponse> customerResponseList = customerService.getAllCustomers();

        logger.info("Number of customers fetched: " + customerResponseList.size());
        return new ResponseEntity<>(customerResponseList, HttpStatus.OK);
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<CustomerAddressResponse> getAddressById(@PathVariable("addressId") Integer addressId) {

        CustomerAddressResponse customerAddressResponse = customerService.getAddressById(addressId);

        logger.info("Address fetched successfully");
        return new ResponseEntity<>(customerAddressResponse, HttpStatus.OK);
    }

    @PatchMapping("/address/add")
    public ResponseEntity<String> addAddress(@RequestBody @Valid CustomerAddressRequest customerAddressRequest) {

        String response = customerService.addAddress(customerAddressRequest);

        logger.info(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/address/update/{id}")
    public ResponseEntity<String> updateAddress(@RequestBody CustomerAddressRequest customerAddressRequest,
                                                @PathVariable Integer id)
    {
        String response = customerService.updateAddress(customerAddressRequest, id);

        logger.info(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/address/delete/{id}")
    public void deleteAddress(@PathVariable Integer id) {

        customerService.deleteAddress(id);
        logger.info("Address with id '" + id + "' was deleted successfully");
    }

}
