package com.ttk.pojo;

import com.ttk.model.ResponseStatus;

public class ResponseData {

	private int lineNumber;
	private ResponseStatus status;
	private String message;

	public ResponseData(int lineNumber, ResponseStatus status, String message) {
		this.lineNumber = lineNumber;
		this.status = status;
		this.message = message;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ResponseData [lineNumber=" + lineNumber + ", status=" + status + ", message=" + message + "]";
	}

}
