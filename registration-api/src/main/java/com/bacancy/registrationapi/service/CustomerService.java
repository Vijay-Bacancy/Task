package com.bacancy.registrationapi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bacancy.registrationapi.dto.model.CustomerRequestDto;
import com.bacancy.registrationapi.dto.model.CustomerResponseDto;
import com.bacancy.registrationapi.dto.model.FileUploadResponse;
import com.bacancy.registrationapi.util.Response;

public interface CustomerService {

	Response<CustomerResponseDto> createCustomer(@Valid CustomerRequestDto customerRequestDto);

	Response<List<CustomerResponseDto>> retrieveAllCustomer();

	Response<CustomerResponseDto> retrieveCustomer(String email);

	Response<CustomerResponseDto> updateCustomer(@Valid CustomerRequestDto customerRequestDto);

	Response<String> deleteCustomer(String email);

	Response<FileUploadResponse> singleFileUpload(MultipartFile file);

	Response<List<FileUploadResponse>> multipleFileUpload(MultipartFile[] files);

	ResponseEntity<Resource> downloadSingleFile(String fileName, HttpServletRequest request);

	Response<FileUploadResponse> singleImageUpload(MultipartFile file);

	Response<FileUploadResponse> singleFileUploadDb(MultipartFile file);

	ResponseEntity<byte[]> downloadSingleFileDb(String fileName, HttpServletRequest request);

	List<String> getAllFileFromDb();

}
