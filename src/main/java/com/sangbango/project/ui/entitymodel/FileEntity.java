package com.sangbango.project.ui.entitymodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.TableGenerator;


@Entity(name = "t_files")
public class FileEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1043524501047334385L;

	@Id
	@TableGenerator(name="t_files", table="sequence_id",
	pkColumnName="sequence_name", pkColumnValue="fileID",
	valueColumnName="sequence_value", allocationSize =1, initialValue=0)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="t_files")
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String fileId;

	@Column(nullable = false, length = 150)
	private String fileName;

	private String fileType;

	@Lob
	private byte[] data;

	public FileEntity() {

	}

	public FileEntity(String fileId, String fileName, String fileType, byte[] data) {
		super();
		this.fileId = fileId;
		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
	}



	public Long getId() {
		return id;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public byte[] getData() {
		return data;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public FileEntity orElseThrow(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
}
