package com.bacancy.registrationapi.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bacancy.registrationapi.dto.mapper.CustomerMapper;
import com.bacancy.registrationapi.dto.model.CustomerRequestDto;
import com.bacancy.registrationapi.dto.model.CustomerResponseDto;
import com.bacancy.registrationapi.dto.model.FileUploadResponse;
import com.bacancy.registrationapi.entity.CustomerEntity;
import com.bacancy.registrationapi.entity.FileEntity;
import com.bacancy.registrationapi.exception.CustomerAlreadyExistsException;
import com.bacancy.registrationapi.exception.CustomerNotFoundException;
import com.bacancy.registrationapi.repository.CustomerRepository;
import com.bacancy.registrationapi.repository.FileRepository;
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
	
	@Value("${file.storage.location}")
	private String fileStorageLocation;
	
	@Autowired
	private FileRepository fileRepository;

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
	
	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(Paths.get(fileStorageLocation));
		} catch (IOException e) {
			throw new RuntimeException("Issue in creating file directory");
		}
	}

	@Override
	public Response<FileUploadResponse> singleFileUpload(MultipartFile file) {
		Path path = Paths.get(fileStorageLocation);
		String fileName = file.getOriginalFilename();
		try {
			Files.copy(file.getInputStream(), path.resolve(fileName));
		} catch (IOException e) {
			throw new RuntimeException("Issue in storing the file", e);
		}
		String url = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/download/")
				.path(fileName)
				.toUriString();
		String contentType = file.getContentType();
		FileUploadResponse uploadResponse = new FileUploadResponse(fileName, contentType, url);
		return new Response<>(HttpStatus.CREATED, uploadResponse);
	}

	@Override
	public Response<List<FileUploadResponse>> multipleFileUpload(MultipartFile[] files) {
		List<FileUploadResponse> FileUploadResponseList = new ArrayList<>();
		Arrays.asList(files)
			.stream()
			.forEach(file -> {
				FileUploadResponse response = singleFileUpload(file).getData();
				FileUploadResponseList.add(response);
			});
		 return new Response<>(HttpStatus.CREATED, FileUploadResponseList);
	}

	@Override
	public ResponseEntity<Resource> downloadSingleFile(String fileName, HttpServletRequest request) {
		Path path = Paths.get(fileStorageLocation).resolve(fileName);
		Resource resource;
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			throw new RuntimeException("Issue in reading the file", e);
		}
		
		if(resource.exists() && resource.isReadable()) {
			String mimeType;
			try {
				mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			} catch (IOException e) {
				mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
			}
			
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(mimeType))
//					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName="+resource.getFileName()) //download
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName="+resource.getFilename()) //render
					.body(resource);
		}else {
			throw new RuntimeException("the file doesn't exist or not readable");
		}
	}

	@Override
	public Response<FileUploadResponse> singleImageUpload(MultipartFile file) {
		String contentType = file.getContentType();
		if(contentType.startsWith("image/")) {
			return singleFileUpload(file);
		}
		throw new RuntimeException("it's not an image");
	}

	@Override
	public Response<FileUploadResponse> singleFileUploadDb(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileEntity fileEntity = new FileEntity();
		fileEntity.setFileName(fileName);
		try {
			fileEntity.setDocFile(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		fileRepository.save(fileEntity);
		String url = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFromDb/")
				.path(fileName)
				.toUriString();
		String contentType = file.getContentType();
		FileUploadResponse response = new FileUploadResponse(fileName, contentType, url);
		return new Response<>(HttpStatus.CREATED, response);
	}

	@Override
	public ResponseEntity<byte[]> downloadSingleFileDb(String fileName, HttpServletRequest request) {
		FileEntity fileEntity = fileRepository.findByFileName(fileName);
		
		String mimeType = request.getServletContext().getMimeType(fileEntity.getFileName());
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(mimeType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName="+fileEntity.getFileName()) //download
//				.header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName="+fileEntity.getFileName()) //render
				.body(fileEntity.getDocFile());
	}
	
	@Override
	public List<String> getAllFileFromDb() {
		List<FileEntity> files = fileRepository.findAll();
		List<String> allFiles = new ArrayList<>();
		files.stream().forEach(file -> {
			allFiles.add(file.getFileName());
		});
		return allFiles;
	}

}
