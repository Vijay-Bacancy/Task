package com.bacancy.registrationapi.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.bacancy.registrationapi.dto.model.CustomerResponseDto;
import com.bacancy.registrationapi.entity.CustomerEntity;

@Component
public class CustomerMapper {
	public static List<CustomerResponseDto> toCustomerDtos(List<CustomerEntity> customerList){
		
		List<CustomerResponseDto> customerResponseDto = 
				customerList.stream().map(customer -> 
					new ModelMapper().map(customer, CustomerResponseDto.class))
						.collect(Collectors.toList());
		
		return customerResponseDto;
	}
}
