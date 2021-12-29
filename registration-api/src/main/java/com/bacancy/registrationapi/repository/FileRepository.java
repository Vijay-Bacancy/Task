package com.bacancy.registrationapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bacancy.registrationapi.entity.FileEntity;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer>{

	FileEntity findByFileName(String fileName);

}
