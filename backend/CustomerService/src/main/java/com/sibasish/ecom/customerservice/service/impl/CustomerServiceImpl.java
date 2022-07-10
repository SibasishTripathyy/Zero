package com.sibasish.ecom.customerservice.service.impl;

import com.sibasish.ecom.customerservice.entity.Customer;
import com.sibasish.ecom.customerservice.entity.CustomerAddress;
import com.sibasish.ecom.customerservice.repository.CustomerAddressRepository;
import com.sibasish.ecom.customerservice.repository.CustomerRepository;
import com.sibasish.ecom.customerservice.repository.RoleRepository;
import com.sibasish.ecom.customerservice.request.CustomerAddressRequest;
import com.sibasish.ecom.customerservice.request.CustomerRequest;
import com.sibasish.ecom.customerservice.response.CustomerResponse;
import com.sibasish.ecom.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sibasish.ecom.customerservice.utils.RoleConstants.CUSTOMER;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    @Autowired
    private EntityManager entityManager;

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
                .mobile
                        (
                                customerAddressRequest.getMobile() != null ?
                                customerAddressRequest.getMobile() :
                                customer.getMobile()
                        )
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

    @Override
    @Transactional
    public String updateAddress(CustomerAddressRequest customerAddressRequest, Integer id) {

        Optional<CustomerAddress> customerAddress = customerAddressRepository.findById(id);

        if (customerAddress.isPresent()) {

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaUpdate<CustomerAddress> criteriaUpdate =
                    criteriaBuilder.createCriteriaUpdate(CustomerAddress.class);
            Root<CustomerAddress> root = criteriaUpdate.from(CustomerAddress.class);


            String mobile = customerAddressRequest.getMobile();
            Integer houseNo = customerAddressRequest.getHouseNo();
            String address_line_1 = customerAddressRequest.getAddressLine1();
            String address_line_2 = customerAddressRequest.getAddressLine2();
            String landmark = customerAddressRequest.getLandmark();
            Integer pinCode = customerAddressRequest.getPinCode();
            String city = customerAddressRequest.getCity();
            String country = customerAddressRequest.getCountry();


            if (mobile != null && !mobile.equals(""))
                criteriaUpdate.set(root.get("mobile"), mobile);

            if (houseNo != null)
                criteriaUpdate.set(root.get("houseNo"), houseNo);

            if (address_line_1 != null && !address_line_1.equals(""))
                criteriaUpdate.set(root.get("address_line_1"), address_line_1);

            if (address_line_2 != null && !address_line_2.equals(""))
                criteriaUpdate.set(root.get("address_line_2"), address_line_2);

            if (landmark != null && !landmark.equals(""))
                criteriaUpdate.set(root.get("landmark"), landmark);

            if (pinCode != null)
                criteriaUpdate.set(root.get("pinCode"), pinCode);

            if (city != null && !city.equals(""))
                criteriaUpdate.set(root.get("city"), city);

            if (country != null && !country.equals(""))
                criteriaUpdate.set(root.get("country"), country);

            criteriaUpdate.where(criteriaBuilder.equal(root.get("customerAddressId"), id));

            entityManager.createQuery(criteriaUpdate).executeUpdate();
        }

        return null;
    }

    @Override
    public String deleteAddress(Integer id) {

        try {
            customerAddressRepository.deleteById(id);
        } catch (Exception e) {
            return "ERROR: Can't find address with id: " + id;
        }

        return null;
    }
}
