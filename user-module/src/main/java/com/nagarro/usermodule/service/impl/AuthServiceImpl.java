package com.nagarro.usermodule.service.impl;

import com.nagarro.usermodule.constant.Constant;
import com.nagarro.usermodule.entity.User;
import com.nagarro.usermodule.exception.UnauthorizedAccessException;
import com.nagarro.usermodule.request.LoginRequest;
import com.nagarro.usermodule.service.AuthService;
import com.nagarro.usermodule.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    @Autowired
    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String authenticateUser(LoginRequest loginRequest) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User existingUser = userService.getUserByEmail(loginRequest.getEmail());
        if (bCryptPasswordEncoder.matches(loginRequest.getPassword(), existingUser.getPassword())) {
            return generateJWTToken(loginRequest);
        }
        throw new UnauthorizedAccessException("Incorrect Credentials", HttpStatus.UNAUTHORIZED.value());
    }
    private String generateJWTToken(LoginRequest loginRequest) {
        User userInformation = userService.getUserByEmail(loginRequest.getEmail());
        Long userId = userInformation.getId();
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + Constant.ONE_HOUR);
        return Jwts.builder()
                .setSubject(loginRequest.getEmail())
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, Constant.SECRET_KEY)
                .compact();
    }
}
