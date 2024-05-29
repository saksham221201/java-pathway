package com.nagarro.usermodule.service;

import com.nagarro.usermodule.entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    List<User> getAllUsers();
    User getUserById(Long userId);
}
