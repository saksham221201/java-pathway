package com.nagarro.pathway.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	private Long id;
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String mobile;
}
