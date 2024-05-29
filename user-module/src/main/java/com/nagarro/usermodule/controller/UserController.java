package com.nagarro.usermodule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.usermodule.entity.User;
import com.nagarro.usermodule.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@PostMapping
	public ResponseEntity<User> addUsers(@RequestBody User user){
		
		User newUser = userService.addUser(user);
		
		return new ResponseEntity<User>(newUser, new HttpHeaders(), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getallUsers(){
		List<User> list = userService.getAllUsers();
        
		return new ResponseEntity<List<User>>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/id")
	public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
		User fetchedUser = userService.getUserById(id);
        
		return new ResponseEntity<User>(fetchedUser, new HttpHeaders(), HttpStatus.OK);
	}
}
