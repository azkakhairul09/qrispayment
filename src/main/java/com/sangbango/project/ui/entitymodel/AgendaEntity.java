package com.sangbango.project.ui.entitymodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.TableGenerator;

@Entity(name = "t_agenda")
public class AgendaEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1334237133313838558L;
	
	@Id
	@TableGenerator(name="t_agenda", table="sequence_id",
	pkColumnName="sequence_name", pkColumnValue="agendaID",
	valueColumnName="sequence_value", allocationSize =1, initialValue=0)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="t_agenda")
	private Long id;
	@Column(nullable = false, length = 100)
	private String title;
	@Column(nullable = false)
	private String image;
	@Lob
	@Column(nullable = false, length = 500)
	private String description;
	@Column(nullable = false, length = 100)
	private String location;
	@Column(nullable = false, length = 25)
	private String createdDate;
	@Column(nullable = false, length = 30)
	private String createdBy;
	@Column(nullable = true, length = 30)
	private String updatedDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	
}
