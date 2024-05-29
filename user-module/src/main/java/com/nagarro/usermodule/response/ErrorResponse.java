package com.nagarro.usermodule.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
	private String message;
	
	private int code;
	
	private LocalDateTime timestamp;

	public ErrorResponse(String message, int code) {
		this.message = message;
		this.code = code;
		this.timestamp = LocalDateTime.now();
	}
}
