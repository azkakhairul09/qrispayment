package com.sangbango.project.ui.entitymodel.response;

public class ContentInvoice {

	private String errorCode;
	private String errorDesc;
	private String timestamp;
	private InvoiceResponse content;
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public InvoiceResponse getContent() {
		return content;
	}
	public void setContent(InvoiceResponse content) {		
		this.content = content;
	}
}
