package com.bacancy.registrationapi.service;

import java.util.List;

import com.bacancy.registrationapi.dto.model.CustomerRequestDto;
import com.bacancy.registrationapi.dto.model.CustomerResponseDto;

public interface CustomerService {

	CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto);

	List<CustomerResponseDto> retrieveAllCustomer();

	CustomerResponseDto retrieveCustomer(String email);

}
