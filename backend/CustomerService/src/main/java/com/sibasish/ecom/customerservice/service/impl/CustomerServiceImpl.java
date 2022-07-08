package com.sibasish.ecom.customerservice.service.impl;

import com.sibasish.ecom.customerservice.entity.Customer;
import com.sibasish.ecom.customerservice.repository.CustomerRepository;
import com.sibasish.ecom.customerservice.repository.RoleRepository;
import com.sibasish.ecom.customerservice.request.CustomerRequest;
import com.sibasish.ecom.customerservice.response.CustomerResponse;
import com.sibasish.ecom.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.sibasish.ecom.customerservice.utils.RoleConstants.CUSTOMER;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerResponse createUser(CustomerRequest customerRequest) {

        Customer customer = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .email(customerRequest.getEmail())
                .password(customerRequest.getPassword())
                .mobile(customerRequest.getMobile())
                .role(roleRepository.findByName(CUSTOMER.toString()))
                .build();

        customer = customerRepository.save(customer);

        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .mobile(customer.getMobile())
                .build();
    }
}
