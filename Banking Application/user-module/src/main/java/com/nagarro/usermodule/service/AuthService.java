package com.nagarro.usermodule.service;

import com.nagarro.usermodule.request.LoginRequest;

public interface AuthService {
    String authenticateUser(LoginRequest loginRequest);
}
