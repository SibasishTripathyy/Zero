package com.sibasish.ecom.customerservice.service;

import com.sibasish.ecom.customerservice.request.CustomerAddressRequest;
import com.sibasish.ecom.customerservice.request.CustomerRequest;
import com.sibasish.ecom.customerservice.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse createUser(CustomerRequest customerRequest);

    List<CustomerResponse> getAllCustomers();

    String addAddress(CustomerAddressRequest customerAddressRequest);

    String updateAddress(CustomerAddressRequest customerAddressRequest, Integer id);

    String deleteAddress(Integer id);
}
