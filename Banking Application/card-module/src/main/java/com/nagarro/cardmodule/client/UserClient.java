package com.nagarro.cardmodule.client;

import com.nagarro.cardmodule.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {

    @GetMapping("/v1/api/users/email/{email}")
    User getUserByEmail(@PathVariable String email);
}