package com.bacancy.registrationapi.dto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerResponseDto {

	private int customerId;
	private String firstname;
	private String lastname;
	private String city;
	private String email;
	private String mobileNo;
	private int age;
	private String gender;
	
}
