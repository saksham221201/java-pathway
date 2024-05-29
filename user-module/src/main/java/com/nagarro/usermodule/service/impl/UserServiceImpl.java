package com.nagarro.usermodule.service.impl;

import com.nagarro.usermodule.dao.UserDao;
import com.nagarro.usermodule.entity.User;
import com.nagarro.usermodule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User addUser(User user) {
        return userDao.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> optionalUser = userDao.findById(userId);
        return optionalUser.orElse(null);
    }
}
