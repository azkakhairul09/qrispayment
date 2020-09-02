package com.sangbango.project.shared.dto;

import java.util.Set;

public class InvoiceDto {
	private Long id;
	private String invoiceId;
	private String invoiceNumber;
	private String invoiceName;
	private Double invoiceNominal;
	private String adminFee;
	private String currency;
	private Integer qty;
	private String createdBy;
	private String content;
	private String invoiceDate;
	private String dueTime;
	private ProductDto product;
	private Set<TransactionDto> transaction;
	private String status;
	private String description;
	private String transactionId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getInvoiceName() {
		return invoiceName;
	}
	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}
	public Double getInvoiceNominal() {
		return invoiceNominal;
	}
	public void setInvoiceNominal(Double invoiceNominal) {
		this.invoiceNominal = invoiceNominal;
	}
	public String getAdminFee() {
		return adminFee;
	}
	public void setAdminFee(String adminFee) {
		this.adminFee = adminFee;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getDueTime() {
		return dueTime;
	}
	public void setDueTime(String dueTime) {
		this.dueTime = dueTime;
	}
	public ProductDto getProduct() {
		return product;
	}
	public void setProduct(ProductDto product) {
		this.product = product;
	}
	public Set<TransactionDto> getTransaction() {
		return transaction;
	}
	public void setTransaction(Set<TransactionDto> transaction) {
		this.transaction = transaction;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
