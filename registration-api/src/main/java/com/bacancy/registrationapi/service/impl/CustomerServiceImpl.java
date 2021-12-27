package com.bacancy.registrationapi.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bacancy.registrationapi.dto.mapper.CustomerMapper;
import com.bacancy.registrationapi.dto.model.CustomerRequestDto;
import com.bacancy.registrationapi.dto.model.CustomerResponseDto;
import com.bacancy.registrationapi.entity.CustomerEntity;
import com.bacancy.registrationapi.exception.CustomerAlreadyExistsException;
import com.bacancy.registrationapi.exception.CustomerNotFoundException;
import com.bacancy.registrationapi.repository.CustomerRepository;
import com.bacancy.registrationapi.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto) {
		CustomerEntity customerEntity = customerRepository.findByEmail(customerRequestDto.getEmail());
		if(customerEntity==null) {
			CustomerEntity customer = modelMapper.map(customerRequestDto, CustomerEntity.class);
			customerRepository.save(customer);
			return modelMapper.map(customer, CustomerResponseDto.class);
		}
		throw new CustomerAlreadyExistsException("Customer already exists with email: "+customerRequestDto.getEmail());
	}

	@Override
	public List<CustomerResponseDto> retrieveAllCustomer() {
		List<CustomerEntity> Customers = customerRepository.findAll();
		return CustomerMapper.toCustomerDtos(Customers);
	}

	@Override
	public CustomerResponseDto retrieveCustomer(String email) {
		CustomerEntity customer = customerRepository.findByEmail(email);
		if(customer==null) {
			throw new CustomerNotFoundException("Customer Not Found with email: "+email);
		}
		return modelMapper.map(customer, CustomerResponseDto.class);		
	}


}
