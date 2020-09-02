package com.sangbango.project.ui.entitymodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity(name = "t_qrencontainer")
public class PaymentNotifQrenContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -79992922626861102L;
	
	@Id
	@TableGenerator(name="t_qrencontainer", table="sequence_id",
	pkColumnName="sequence_name", pkColumnValue="qrenInvoiceID",
	valueColumnName="sequence_value", allocationSize =1, initialValue=0)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="t_qrencontainer")
	private Long id;
	@Column(nullable = false, length = 15)
	private String invoice;
	@Column(nullable = false, length = 5)
	private String status;
	@Column(nullable = false, length = 15)
	private String amount;
	@Column(nullable = false, length = 25)
	private String merchantApiKey;
	@Column(nullable = true, length = 125)
	private String trxId;
	@Column(nullable = true, length = 125)
	private String qrentransid;
	@Column(nullable = false, length = 15)
	private String message;
	@Column(nullable = false, length = 30)
	private String timeStamp;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getMerchantApiKey() {
		return merchantApiKey;
	}
	public void setMerchantApiKey(String merchantApiKey) {
		this.merchantApiKey = merchantApiKey;
	}
	public String getTrxId() {
		return trxId;
	}
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
	public String getQrentransid() {
		return qrentransid;
	}
	public void setQrentransid(String qrentransid) {
		this.qrentransid = qrentransid;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
}
