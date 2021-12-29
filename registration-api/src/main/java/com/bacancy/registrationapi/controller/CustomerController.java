package com.bacancy.registrationapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bacancy.registrationapi.dto.model.CustomerRequestDto;
import com.bacancy.registrationapi.dto.model.CustomerResponseDto;
import com.bacancy.registrationapi.dto.model.FileUploadResponse;
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
	
//	local system
	@PostMapping("/upload")
	public Response<FileUploadResponse> singleFileUpload(@RequestParam MultipartFile file) {
		return customerService.singleFileUpload(file);
	}
	
	@PostMapping("/upload/multiple")
	public Response<List<FileUploadResponse>> multipleFileUpload(@RequestParam MultipartFile[] files){
		return customerService.multipleFileUpload(files);
	}
	
	@GetMapping("/download/{fileName}")
	public ResponseEntity<Resource> downloadSingleFile(@PathVariable String fileName, HttpServletRequest request){
		return customerService.downloadSingleFile(fileName,request);
	}
	
	@PostMapping("/upload/image")
	public Response<FileUploadResponse> singleImageUpload(@RequestParam MultipartFile file) {
		return customerService.singleImageUpload(file);
	}
	
	@PostMapping("/uploadDb")
	public Response<FileUploadResponse> singleFileUploadDb(@RequestParam MultipartFile file) {
		return customerService.singleFileUploadDb(file);
	}
	
	@GetMapping("/downloadFromDb/{fileName}")
	public ResponseEntity<byte[]> downloadSingleFileDb(@PathVariable String fileName, HttpServletRequest request){
		return customerService.downloadSingleFileDb(fileName,request);
	}
	
	@GetMapping("/downloadFromDb")
	public List<String> GetAllFileFromDb(){
		return customerService.getAllFileFromDb();
	}

}
