package com.sibasish.ecom.customerservice.service.impl;

import com.sibasish.ecom.customerservice.entity.Customer;
import com.sibasish.ecom.customerservice.entity.CustomerAddress;
import com.sibasish.ecom.customerservice.repository.CustomerRepository;
import com.sibasish.ecom.customerservice.repository.RoleRepository;
import com.sibasish.ecom.customerservice.request.CustomerAddressRequest;
import com.sibasish.ecom.customerservice.request.CustomerRequest;
import com.sibasish.ecom.customerservice.response.CustomerResponse;
import com.sibasish.ecom.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<CustomerResponse> getAllCustomers() {

        List<Customer> customerList = customerRepository.findAll();

        List<CustomerResponse> customerResponseList = new ArrayList<>();
        customerList.forEach(customer ->
                customerResponseList.add
                        (
                                CustomerResponse.builder()
                                .customerId(customer.getCustomerId())
                                .firstName(customer.getFirstName())
                                .lastName(customer.getLastName())
                                .email(customer.getEmail())
                                .mobile(customer.getMobile())
                                .build()
                )
        );

        return customerResponseList;
    }

    @Override
    public String addAddress(CustomerAddressRequest customerAddressRequest) {

        Customer customer = customerRepository.findByEmail(customerAddressRequest.getEmail());

        if (customer == null)
            return "ERROR: Could not find customer with email id: " + customerAddressRequest.getEmail();

        System.out.println(customer.getCustomerId() + " -> " + customer.getEmail());

        CustomerAddress customerAddress = CustomerAddress.builder()
                .mobile(customerAddressRequest.getMobile())
                .houseNo(customerAddressRequest.getHouseNo())
                .addressLine1(customerAddressRequest.getAddressLine1())
                .addressLine2(customerAddressRequest.getAddressLine2())
                .landmark(customerAddressRequest.getLandmark())
                .pinCode(customerAddressRequest.getPinCode())
                .city(customerAddressRequest.getCity())
                .country(customerAddressRequest.getCountry())
                .build();

        customer.getCustomerAddressList().add(customerAddress);

        customerRepository.save(customer);
        return null;
    }
}
