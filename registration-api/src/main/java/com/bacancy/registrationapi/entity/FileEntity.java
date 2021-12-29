package com.bacancy.registrationapi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="file")
@NoArgsConstructor
@Getter
@Setter
public class FileEntity {
	
	@Id
	@GeneratedValue
	private int id;
	private String fileName;
	@Lob
	private byte[] docFile;

}
