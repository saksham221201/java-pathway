package com.nagarro.loanmodule.response;

import com.nagarro.loanmodule.dto.User;
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
