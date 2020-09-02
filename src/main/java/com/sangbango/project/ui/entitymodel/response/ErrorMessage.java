package com.sangbango.project.ui.entitymodel.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorMessage {
	private String errorCode;
	private String errorDesc;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT+7")
	private Date timestamp;

	public ErrorMessage(String errorCode, String errorDesc, Date timestamp) {
		super();
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
		this.timestamp = timestamp;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

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
}
