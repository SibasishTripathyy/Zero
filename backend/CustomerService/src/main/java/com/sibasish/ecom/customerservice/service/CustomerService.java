package com.sibasish.ecom.customerservice.service;

import com.sibasish.ecom.customerservice.request.CustomerAddressRequest;
import com.sibasish.ecom.customerservice.request.CustomerRequest;
import com.sibasish.ecom.customerservice.response.CustomerAddressResponse;
import com.sibasish.ecom.customerservice.response.CustomerResponse;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    CustomerResponse createUser(CustomerRequest customerRequest);

    CustomerResponse getCustomerById(UUID customerId);

    List<CustomerResponse> getAllCustomers();

    CustomerAddressResponse getAddressById(Integer addressId);

    String addAddress(CustomerAddressRequest customerAddressRequest);

    String updateAddress(CustomerAddressRequest customerAddressRequest, Integer id);

    void deleteAddress(Integer id);
}
