package com.sibasish.ecom.customerservice.controller;

import com.sibasish.ecom.customerservice.request.CustomerAddressRequest;
import com.sibasish.ecom.customerservice.request.CustomerRequest;
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

@RestController
@RequestMapping("/customer")
public class CustomerController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> createUser(@RequestBody CustomerRequest customerRequest) {

        CustomerResponse customerResponse = customerService.createUser(customerRequest);

        logger.info("Customer with email " + customerResponse.getEmail() + "was created");
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {

        List<CustomerResponse> customerResponseList = customerService.getAllCustomers();

        logger.info("Number of customers fetched: " + customerResponseList.size());
        return new ResponseEntity<>(customerResponseList, HttpStatus.OK);
    }

    @PatchMapping("/address/add")
    public ResponseEntity<String> addAddress(@RequestBody @Valid CustomerAddressRequest customerAddressRequest) {

        String response = customerService.addAddress(customerAddressRequest);

        if (response != null && response.startsWith("ERROR")) {

            logger.error(response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        String success_message = "Address has been added successfully.";

        logger.info(success_message);
        return new ResponseEntity<>(success_message, HttpStatus.OK);
    }

    @PatchMapping("/address/update/{id}")
    public ResponseEntity<String> updateAddress(@RequestBody CustomerAddressRequest customerAddressRequest,
                                                @PathVariable Integer id)
    {

        String response = customerService.updateAddress(customerAddressRequest, id);

        if (response != null && response.startsWith("ERROR")) {

            logger.error(response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        String success_message = "Address has been updated successfully.";

        logger.info(success_message);
        return new ResponseEntity<>(success_message, HttpStatus.OK);
    }


    @DeleteMapping("/address/delete/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Integer id) {

        String response = customerService.deleteAddress(id);

        if (response != null && response.startsWith("ERROR")) {

            logger.error(response);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        logger.info("Address with id '" + id + "' was deleted successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
