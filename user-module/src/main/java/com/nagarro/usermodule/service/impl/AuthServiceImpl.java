package com.nagarro.usermodule.service.impl;

import com.nagarro.usermodule.request.LoginRequest;
import com.nagarro.usermodule.service.AuthService;
import com.nagarro.usermodule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    @Autowired
    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String authenticateUser(LoginRequest loginRequest) {
        return "";
    }
}
