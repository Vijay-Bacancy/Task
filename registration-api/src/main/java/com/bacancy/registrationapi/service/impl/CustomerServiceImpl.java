package com.bacancy.registrationapi.service.impl;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bacancy.registrationapi.dto.mapper.CustomerMapper;
import com.bacancy.registrationapi.dto.model.CustomerRequestDto;
import com.bacancy.registrationapi.dto.model.CustomerResponseDto;
import com.bacancy.registrationapi.entity.CustomerEntity;
import com.bacancy.registrationapi.exception.CustomerAlreadyExistsException;
import com.bacancy.registrationapi.exception.CustomerNotFoundException;
import com.bacancy.registrationapi.repository.CustomerRepository;
import com.bacancy.registrationapi.service.CustomerService;
import com.bacancy.registrationapi.util.Response;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Response<CustomerResponseDto> createCustomer(@Valid CustomerRequestDto customerRequestDto) {
		CustomerEntity customerEntity = customerRepository.findByEmail(customerRequestDto.getEmail());
		if(customerEntity==null) {
			CustomerEntity customer = modelMapper.map(customerRequestDto, CustomerEntity.class);
			customer.setPassword(passwordEncoder.encode(customer.getPassword()));
			customerRepository.save(customer);
			return new Response<>(HttpStatus.CREATED, modelMapper.map(customer, CustomerResponseDto.class));
		}
		throw new CustomerAlreadyExistsException("Customer already exists with email: "+customerRequestDto.getEmail());
	}

	@Override
	public Response<List<CustomerResponseDto>> retrieveAllCustomer() {
		List<CustomerEntity> Customers = customerRepository.findAll();
		return new Response<>(HttpStatus.OK, CustomerMapper.toCustomerDtos(Customers));
	}

	@Override
	public Response<CustomerResponseDto> retrieveCustomer(String email) {
		CustomerEntity customer = customerRepository.findByEmail(email);
		if(customer==null) {
			throw new CustomerNotFoundException("Customer Not Found with email: "+email);
		}
		return new Response<>(HttpStatus.OK, modelMapper.map(customer, CustomerResponseDto.class));
	}

	@Override
	public Response<CustomerResponseDto> updateCustomer(@Valid CustomerRequestDto customerRequestDto) {
		String email = customerRequestDto.getEmail();
		CustomerEntity customer = customerRepository.findByEmail(email);
		if(customer==null) {
			throw new CustomerNotFoundException("Customer Not Found with email: "+email);
		}
		customer.setFirstname(customerRequestDto.getFirstname());
		customer.setLastname(customerRequestDto.getLastname());
		customer.setCity(customerRequestDto.getCity());
		customer.setMobileNo(customerRequestDto.getMobileNo());
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		customer.setAge(customerRequestDto.getAge());
		customer.setGender(customerRequestDto.getGender());
		customerRepository.save(customer);
		return new Response<>(HttpStatus.OK, modelMapper.map(customer, CustomerResponseDto.class));
	}

	@Override
	public Response<String> deleteCustomer(String email) {
		CustomerEntity customer = customerRepository.findByEmail(email);
		if(customer==null) {
			throw new CustomerNotFoundException("Customer Not Found with email: "+email);
		}
		customerRepository.delete(customer);
		return new Response<>(HttpStatus.OK, "Customer deleted with email: "+email);
	}


}
