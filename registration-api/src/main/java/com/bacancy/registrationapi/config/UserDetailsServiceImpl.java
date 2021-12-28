package com.bacancy.registrationapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bacancy.registrationapi.entity.CustomerEntity;
import com.bacancy.registrationapi.exception.CustomerNotFoundException;
import com.bacancy.registrationapi.repository.CustomerRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		CustomerEntity customer = customerRepository.findByEmail(email);
		if(customer==null) {
			throw new UsernameNotFoundException("User Not Found with email: "+email);
		}
		System.out.println(passwordEncoder.encode(customer.getPassword()));
		return new CustomUserDetails(customer);
	}

}
