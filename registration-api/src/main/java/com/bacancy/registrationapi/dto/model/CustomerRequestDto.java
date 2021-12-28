package com.bacancy.registrationapi.dto.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerRequestDto {
	
	@NotNull
	private String firstname;
	@NotNull
	private String lastname;
	@NotNull
	private String city;
	@Email
	@NotNull
	private String email;
	@NotNull
	private String password;
	@NotNull
	private String mobileNo;
	@NotNull
	@Max(150)
	private int age;
	@NotNull
	private String gender;
	
}
