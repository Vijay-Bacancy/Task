package com.bacancy.registrationapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bacancy.registrationapi.dto.model.CustomerRequestDto;
import com.bacancy.registrationapi.dto.model.CustomerResponseDto;
import com.bacancy.registrationapi.service.CustomerService;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/customers")
	public CustomerResponseDto createCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
		return customerService.createCustomer(customerRequestDto);
	}
	
	@GetMapping("/customers")
	public List<CustomerResponseDto> retrieveAllCustomer(){
		return customerService.retrieveAllCustomer();
	}
	
	@GetMapping("/customers/{email}")
	public CustomerResponseDto retrieveCustomer(@PathVariable String email) {
		return customerService.retrieveCustomer(email);
	}

}
