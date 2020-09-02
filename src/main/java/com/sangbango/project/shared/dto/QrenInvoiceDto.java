package com.sangbango.project.shared.dto;

public class QrenInvoiceDto {
	private String merchantApiKey;
	private String nominal;
	private String staticQr;
	private String invoiceName;
	private String qrGaruda;
	private String info;
	private String trxId;
	
	public String getMerchantApiKey() {
		return merchantApiKey;
	}
	public void setMerchantApiKey(String merchantApiKey) {
		this.merchantApiKey = merchantApiKey;
	}
	public String getNominal() {
		return nominal;
	}
	public void setNominal(String nominal) {
		this.nominal = nominal;
	}
	public String getStaticQr() {
		return staticQr;
	}
	public void setStaticQr(String staticQr) {
		this.staticQr = staticQr;
	}
	public String getInvoiceName() {
		return invoiceName;
	}
	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}
	public String getQrGaruda() {
		return qrGaruda;
	}
	public void setQrGaruda(String qrGaruda) {
		this.qrGaruda = qrGaruda;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getTrxId() {
		return trxId;
	}
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
}
