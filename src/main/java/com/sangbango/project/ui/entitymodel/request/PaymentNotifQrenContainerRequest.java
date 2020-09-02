package com.sangbango.project.ui.entitymodel.request;

public class PaymentNotifQrenContainerRequest {
	private String invoice;
	private String status;
	private String amount;
	private String merchantApiKey;
	private String trxId;
	private String qrentransid;
	private String message;
	
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
}
