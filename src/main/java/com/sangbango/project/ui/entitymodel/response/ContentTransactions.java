package com.sangbango.project.ui.entitymodel.response;

import java.util.List;

public class ContentTransactions {
	private String errorCode;
	private String errorDesc;
	private String timestamp;
	private List<TransactionResponse> content;
	
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
	public List<TransactionResponse> getContent() {
		return content;
	}
	public void setContent(List<TransactionResponse> content) {
		this.content = content;
	}
}
