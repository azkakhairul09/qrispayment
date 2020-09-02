package com.sangbango.project.ui.entitymodel.response;

public class ContentAgenda {

	private String errorCode;
	private String errorDesc;
	private String timestamp;
	private AgendaResponse content;
	
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
	public AgendaResponse getContent() {
		return content;
	}
	public void setContent(AgendaResponse content) {		
		this.content = content;
	}
}
