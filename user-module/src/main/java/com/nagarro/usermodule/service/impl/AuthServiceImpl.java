package com.nagarro.usermodule.service.impl;

import com.nagarro.usermodule.constant.Constant;
import com.nagarro.usermodule.entity.User;
import com.nagarro.usermodule.exception.UnauthorizedAccessException;
import com.nagarro.usermodule.request.LoginRequest;
import com.nagarro.usermodule.service.AuthService;
import com.nagarro.usermodule.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String authenticateUser(LoginRequest loginRequest) {
        logger.debug("Inside authenticate User");

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User existingUser = userService.getUserByEmail(loginRequest.getEmail());
        if (bCryptPasswordEncoder.matches(loginRequest.getPassword(), existingUser.getPassword())) {
            logger.info("Generating JWT Token");
            return generateJWTToken(loginRequest);
        }
        logger.error("Incorrect Credentials");
        throw new UnauthorizedAccessException("Incorrect Credentials", HttpStatus.UNAUTHORIZED.value());
    }

    private String generateJWTToken(LoginRequest loginRequest) {
        logger.debug("Inside generateJWTToken function");

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
