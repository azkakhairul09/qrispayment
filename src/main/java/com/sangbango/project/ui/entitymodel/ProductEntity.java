package com.sangbango.project.ui.entitymodel;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

@Entity(name = "t_product")
public class ProductEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5649213395070886405L;
	@Id
	@TableGenerator(name="t_product", table="sequence_id",
	pkColumnName="sequence_name", pkColumnValue="productID",
	valueColumnName="sequence_value", allocationSize =1, initialValue=0)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="t_product")
	private Long id;
	@Column(nullable = false, length = 25)
	private String productId;
	@Column(nullable = false, length = 25)
	private String merchantId;
	@Column(nullable = false, length = 25)
	private String categorize;
	@Column(nullable = false, length = 50)
	private String productName;
	@Lob
	@Column(nullable = true, length = 500)
	private String productImage;
	@Column(nullable = true, length = 25)
	private String imageId;
	@Lob
	@Column(nullable = true, length = 500)
	private String productDesc;
	@Column(nullable = false)
	private Double totalPrice;
	@Column(nullable = false)
	private Double adminPrice;
	@Column(nullable = false)
	private Double price;
	@Column(nullable = false, length = 30)
	private String createdBy;
	@Column(nullable = false, length = 25)
	private String createdDate;
	@Column(nullable = true, length = 25)
	private String updatedDate;
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private Set<InvoiceEntity> invoice;
	@Column(nullable = true)
	private Boolean isActive;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getCategorize() {
		return categorize;
	}
	public void setCategorize(String categorize) {
		this.categorize = categorize;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Double getAdminPrice() {
		return adminPrice;
	}
	public void setAdminPrice(Double adminPrice) {
		this.adminPrice = adminPrice;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Set<InvoiceEntity> getInvoice() {
		return invoice;
	}
	public void setInvoice(Set<InvoiceEntity> invoice) {
		this.invoice = invoice;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
