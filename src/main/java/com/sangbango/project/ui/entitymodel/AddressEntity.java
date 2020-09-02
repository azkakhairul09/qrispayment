package com.sangbango.project.ui.entitymodel;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

@Entity(name="t_address")
public class AddressEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3831862288475970524L;
	
	@Id
	@TableGenerator(name="t_address", table="sequence_id",
	pkColumnName="sequence_name", pkColumnValue="addressID",
	valueColumnName="sequence_value", allocationSize =1, initialValue=0)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="t_address")
	private Long id;
	
	@Column(nullable = false, length = 25)
	private String addressId;
	
	@Column(nullable = false, length = 255)
	private String fullAddress;
	
	@Column(nullable = false, length = 50)
	private String province;
	
	@Column(nullable = false, length = 50)
	private String city;
	
	@Column(nullable = false, length = 50)
	private String subDistrict;
	
	@Column(nullable = false, length = 50)
	private String village;
	
	@Column(nullable = false, length = 5)
	private String postalCode;
	
	@OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
	private Set<UserEntity> user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSubDistrict() {
		return subDistrict;
	}

	public void setSubDistrict(String subDistrict) {
		this.subDistrict = subDistrict;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Set<UserEntity> getUser() {
		return user;
	}

	public void setUser(Set<UserEntity> user) {
		this.user = user;
	}
}
