package com.nagarro.OAuth2.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    @GetMapping("/user")
    public Map<String,Object> user(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return Collections.singletonMap("name", "Anonymous");
        }
        return Map.of("name", principal.getAttribute("name"),
                       "login",principal.getAttribute("login")
        );
    }

    @GetMapping("/logout")
    public String logout(){
        return "redirect:/";
    }
}
