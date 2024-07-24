package com.nagarro.accountmodule.service;

import com.nagarro.accountmodule.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    @GetMapping("/v1/api/users/userId/{id}")
    User getUserById(@PathVariable("id") Long id);

    @GetMapping("/v1/api/users/email/{email}")
    User getUserByEmail(@PathVariable("email") String email);
}
