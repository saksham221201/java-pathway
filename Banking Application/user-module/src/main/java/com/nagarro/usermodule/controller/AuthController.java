package com.nagarro.usermodule.controller;

import com.nagarro.usermodule.entity.User;
import com.nagarro.usermodule.request.LoginRequest;
import com.nagarro.usermodule.response.LoginResponse;
import com.nagarro.usermodule.service.AuthService;
import com.nagarro.usermodule.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final UserService userService;

    // Constructor Autowiring
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        String token = authService.authenticateUser(loginRequest);
        User existingUser = userService.getUserByEmail(loginRequest.getEmail());
        LoginResponse loginResponse = new LoginResponse(existingUser, token);
        logger.info("Login is successful");
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
}
