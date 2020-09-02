package com.sangbango.project.ui.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sangbango.project.ui.entitymodel.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long>{

	FileEntity findByFileName(String fileName);

}
