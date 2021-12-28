package com.bacancy.registrationapi.service;

import java.util.List;

import javax.validation.Valid;

import com.bacancy.registrationapi.dto.model.CustomerRequestDto;
import com.bacancy.registrationapi.dto.model.CustomerResponseDto;
import com.bacancy.registrationapi.util.Response;

public interface CustomerService {

	Response<CustomerResponseDto> createCustomer(@Valid CustomerRequestDto customerRequestDto);

	Response<List<CustomerResponseDto>> retrieveAllCustomer();

	Response<CustomerResponseDto> retrieveCustomer(String email);

	Response<CustomerResponseDto> updateCustomer(@Valid CustomerRequestDto customerRequestDto);

	Response<String> deleteCustomer(String email);

}
