package com.bacancy.registrationapi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerEntity {
	
	@Id
	@GeneratedValue
	private int customerId;
	private String firstname;
	private String lastname;
	private String city;
	private String email;
	private String password;
	private String mobileNo;
	private int age;
	private String gender;

}
