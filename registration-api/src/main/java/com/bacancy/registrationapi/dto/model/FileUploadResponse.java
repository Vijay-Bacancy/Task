package com.bacancy.registrationapi.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FileUploadResponse {

	private String fileName;
	private String contentType;
	private String url;
	
}
