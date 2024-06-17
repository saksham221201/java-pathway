package com.nagarro.usermodule.service;

import com.nagarro.usermodule.entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    User addAdmin(User user);
    User seedAdmin(User user);
    List<User> getAllUsers();
    User getUserById(Long userId);
    User getUserByEmail(String email);
    User updateUser(Long userId, User user);
    void deleteUser(Long userId);
}
