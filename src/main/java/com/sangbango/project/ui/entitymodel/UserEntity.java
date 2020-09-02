package com.sangbango.project.ui.entitymodel;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity(name="t_user")
public class UserEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3911098816784782934L;
	
	@Id
	@TableGenerator(name="t_user", table="sequence_id",
	pkColumnName="sequence_name", pkColumnValue="userID",
	valueColumnName="sequence_value", allocationSize =1, initialValue=0)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="t_user")
	private Long id;
	@Column(nullable = false, length = 30)
	private String userId;
	@Column(nullable = true, length = 30)
	private String merchantId;
	@Column(nullable = false, length = 100)
	private String name;
	@Column(nullable = false, length = 100)
	private String email;
	@Column(nullable = false, length = 15)
	private String phoneNumber;
	@Lob
	@Column(nullable = true, length = 500)
	private String userImage;
	@Column(nullable = false, length = 255)
	private String encryptPassword;
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="role_id")
	private RoleEntity role;
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="address_id")
	private AddressEntity address;
	@Column(nullable = false, length = 2)
	private Boolean isActive;
	@Column(nullable = false, length = 50)
	private String createdDate;
	@Column(nullable = true, length = 50)
	private String loginTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserImage() {
		return userImage;
	}
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	public String getEncryptPassword() {
		return encryptPassword;
	}
	public void setEncryptPassword(String encryptPassword) {
		this.encryptPassword = encryptPassword;
	}
	public RoleEntity getRole() {
		return role;
	}
	public void setRole(RoleEntity role) {
		this.role = role;
	}
	public AddressEntity getAddress() {
		return address;
	}
	public void setAddress(AddressEntity address) {
		this.address = address;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
}
