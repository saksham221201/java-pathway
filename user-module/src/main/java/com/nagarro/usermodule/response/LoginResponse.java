package com.nagarro.usermodule.response;

import com.nagarro.usermodule.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	
	private User user;
	private String token;
}
