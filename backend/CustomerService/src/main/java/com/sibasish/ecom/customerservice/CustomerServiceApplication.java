package com.sibasish.ecom.customerservice;

import com.sibasish.ecom.customerservice.entity.Role;
import com.sibasish.ecom.customerservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import javax.annotation.PostConstruct;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class CustomerServiceApplication {

	@Autowired
	private RoleRepository roleRepository;
	@PostConstruct
	private void init() {
		roleRepository.save(new Role(1, "CUSTOMER"));
		roleRepository.save(new Role(2, "ADMIN"));
	}

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

}
