package com.sangbango.project.shared.dto;

import java.util.Set;

public class ProductDto {
	private Long id;
	private String productId;
	private String categorize;
	private String productName;
	private String productImage;
	private String productDesc;
	private Double price;
	private Double totalPrice;
	private Double adminPrice;
	private String createdBy;
	private String createdDate;
	private Boolean isActive;
	private Set<InvoiceDto> invoice;
	
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Set<InvoiceDto> getInvoice() {
		return invoice;
	}
	public void setInvoice(Set<InvoiceDto> invoice) {
		this.invoice = invoice;
	}
}
