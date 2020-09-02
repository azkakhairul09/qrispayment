package com.sangbango.project.shared.dto;

public class TransactionDto {
	private Long id;
	private String transactionId;
	private String merchantId;
	private String qrenTransactionId;
	private InvoiceDto invoice;
	private String amount;
	private String description;
	private String transactionDate;
	private String status;
	private Boolean isConfirmed;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getQrenTransactionId() {
		return qrenTransactionId;
	}
	public void setQrenTransactionId(String qrenTransactionId) {
		this.qrenTransactionId = qrenTransactionId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public InvoiceDto getInvoice() {
		return invoice;
	}
	public void setInvoice(InvoiceDto invoice) {
		this.invoice = invoice;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Boolean getIsConfirmed() {
		return isConfirmed;
	}
	public void setIsConfirmed(Boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
}
