package com.nagarro.usermodule.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import com.nagarro.usermodule.entity.User;
import com.nagarro.usermodule.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> addUsers(@RequestBody User user){
		User newUser = userService.addUser(user);
		logger.info("New User Created");
		return new ResponseEntity<>(newUser, new HttpHeaders(), HttpStatus.CREATED);
	}

	//Authorize only those that have ADMIN as their Authority, Authority defined in JwtAuthFilter
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/admin")
	public ResponseEntity<User> addAdmin(@RequestBody User user){
		logger.debug("Inside add admin");
		User newAdmin = userService.addAdmin(user);
		logger.info("New Admin Created");
		return new ResponseEntity<>(newAdmin, new HttpHeaders(), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> list = userService.getAllUsers();
		logger.info("Got List of users");
		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}

	//Only admin can access and the user can access only their own.
	@PreAuthorize("hasAuthority('ADMIN') or (hasAuthority('USER') and #id == principal.id)")
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
		User fetchedUser = userService.getUserById(id);
		logger.info("Fetched a user by id");
		return new ResponseEntity<>(fetchedUser, new HttpHeaders(), HttpStatus.OK);
	}

	//#id is from path variable and principal is the logged-in user
	@PreAuthorize("hasAuthority('ADMIN') or (hasAuthority('USER') and #id == principal.id)")
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		User updatedUser = userService.updateUser(id, user);
		logger.info("Updated user with id:{}", id);
		return new ResponseEntity<>(updatedUser, new HttpHeaders(), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN') or (hasAuthority('USER') and #id == principal.id)")
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
		userService.deleteUser(id);
		logger.info("Deleted user of id:{}", id);
		return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
	}
}
