package com.sangbango.project.ui.entitymodel;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity(name = "t_transaction")
public class TransactionEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8054522000768097213L;
	@Id
	@TableGenerator(name="t_transaction", table="sequence_id",
	pkColumnName="sequence_name", pkColumnValue="transactionID",
	valueColumnName="sequence_value", allocationSize =1, initialValue=0)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="t_transaction")
	private Long id;
	@Column(nullable = false, length = 50)
	private String transactionId;
	@Column(nullable = false, length = 75)
	private String qrenTransactionId;
	@Column(nullable = false, length = 75)
	private String merchantId;
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "invoice_id")
	private InvoiceEntity invoice;
	@Column(nullable = false, length = 20)
	private String amount;
	@Column(nullable = false, length = 25)
	private String description;
	@Column(nullable = false, length = 25)
	private String transactionDate;
	@Column(nullable = false, length = 25)
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
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public InvoiceEntity getInvoice() {
		return invoice;
	}
	public void setInvoice(InvoiceEntity invoice) {
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
