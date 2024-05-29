package com.nagarro.usermodule.response;

import java.time.LocalDateTime;

public class ErrorResponse {
	private String message;
	
	private int code;
	
	private LocalDateTime timestamp;

	public ErrorResponse() {
		super();
	}

	public ErrorResponse(String message, int code) {
		this.message = message;
		this.code = code;
		this.timestamp = LocalDateTime.now();
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
