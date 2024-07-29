package com.nagarro.loanmodule.client;

import com.nagarro.loanmodule.dto.User;
import com.nagarro.loanmodule.request.LoginRequest;
import com.nagarro.loanmodule.response.LoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    @GetMapping("/v1/api/users/email/{email}")
    User getUserByEmail(@PathVariable String email);

    @PostMapping("/v1/api/auth/login")
    LoginResponse login(@RequestBody LoginRequest loginRequest);
}