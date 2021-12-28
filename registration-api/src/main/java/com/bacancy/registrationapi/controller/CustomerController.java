package com.bacancy.registrationapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bacancy.registrationapi.dto.model.CustomerRequestDto;
import com.bacancy.registrationapi.dto.model.CustomerResponseDto;
import com.bacancy.registrationapi.service.CustomerService;
import com.bacancy.registrationapi.util.Response;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/customers")
	public Response<CustomerResponseDto> createCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
		return customerService.createCustomer(customerRequestDto);
	}
	
	@GetMapping("/customers")
	public Response<List<CustomerResponseDto>> retrieveAllCustomer(){
		return customerService.retrieveAllCustomer();
	}
	
	@GetMapping("/customers/{email}")
	public Response<CustomerResponseDto> retrieveCustomer(@PathVariable String email) {
		return customerService.retrieveCustomer(email);
	}
	
	@PutMapping("/customers")
	public Response<CustomerResponseDto> updateCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
		return customerService.updateCustomer(customerRequestDto);
	}
	
	@DeleteMapping("/customers/{email}")
	public Response<String> deleteCustomer(@PathVariable String email) {
		return customerService.deleteCustomer(email);
	}

}
