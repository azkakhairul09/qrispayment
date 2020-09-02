package com.sangbango.project.ui.entitymodel;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

@Entity(name = "t_invoice")
public class InvoiceEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2778434302792197362L;
	@Id
	@TableGenerator(name="t_invoice", table="sequence_id",
	pkColumnName="sequence_name", pkColumnValue="invoiceID",
	valueColumnName="sequence_value", allocationSize =1, initialValue=0)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="t_invoice")
	private Long id;
	@Column(nullable = false, length = 30)
	private String invoiceId;
	@Column(nullable = false, length = 30)
	private String invoiceNumber;
	@Column(nullable = false, length = 150)
	private String invoiceName;
	@Column(nullable = false)
	private Double invoiceNominal;
	@Column(nullable = false, length = 5)
	private String adminFee;
	@Column(nullable = false, length = 5)
	private String currency;
	@Column(nullable = false, length = 5)
	private Integer qty;
	@Column(nullable = false, length = 30)
	private String createdBy;
	@Column(nullable = false, length = 255)
	private String content;
	@Column(nullable = false, length = 25)
	private String invoiceDate;
	@Column(nullable = false, length = 50)
	private String createdDate;
	@Column(nullable = true, length = 50)
	private String modifiedDate;
	@Column(nullable = false, length = 25)
	private String dueTime;
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "product_id")
	private ProductEntity product;
	@OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
	private Set<TransactionEntity> transaction;
	@Column(nullable = false, length = 15)
	private String status;
	@Column(nullable = false, length = 150)
	private String description;
	@Column(nullable = false)
	private Boolean isVerified;
	@Column(nullable = true, length = 50)
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
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getDueTime() {
		return dueTime;
	}
	public void setDueTime(String dueTime) {
		this.dueTime = dueTime;
	}
	public ProductEntity getProduct() {
		return product;
	}
	public void setProduct(ProductEntity product) {
		this.product = product;
	}
	public Set<TransactionEntity> getTransaction() {
		return transaction;
	}
	public void setTransaction(Set<TransactionEntity> transaction) {
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
	public Boolean getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
