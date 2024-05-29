package com.nagarro.usermodule.service.impl;

import com.nagarro.usermodule.dao.UserDao;
import com.nagarro.usermodule.entity.User;
import com.nagarro.usermodule.exception.EmptyInputException;
import com.nagarro.usermodule.exception.RecordAlreadyExistsException;
import com.nagarro.usermodule.exception.RecordNotFoundException;
import com.nagarro.usermodule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User addUser(User user) {
        // Checking if any of the fields is Empty or not
        if(user.getEmail().isBlank() || user.getFirstName().isBlank() || user.getLastName().isBlank() || user.getPassword().isBlank()){
            throw new EmptyInputException("Input cannot be null!!", HttpStatus.BAD_REQUEST.value());
        }

        // Checking if the user with the given email already exists or not
        Optional<User> optionalUser = userDao.findByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            throw new RecordAlreadyExistsException("User already exists with email: " + user.getEmail(), HttpStatus.BAD_REQUEST.value());
        }

        // Saving user to database
        return userDao.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        // Checking if the user with the given userId already exists or not
        Optional<User> optionalUser = userDao.findById(userId);
        if(optionalUser.isEmpty()){
            throw new RecordNotFoundException("User not found with id: " + userId, HttpStatus.NOT_FOUND.value());
        }

        return optionalUser.get();
    }

    @Override
    public User updateUser(Long userId, User user) {
        // Checking if any of the inputs is null
        if(user.getEmail().isBlank() || user.getFirstName().isBlank() || user.getLastName().isBlank() || user.getPassword().isBlank()){
            throw new EmptyInputException("Input cannot be null", HttpStatus.BAD_REQUEST.value());
        }

        // Checking if the user with the given id already exists or not
        Optional<User> existingUser = userDao.findById(userId);
        if(existingUser.isEmpty()){
            throw new RecordNotFoundException("User not found with id: " + userId, HttpStatus.NOT_FOUND.value());
        }

        User updateUser = existingUser.get();
        updateUser.setEmail(user.getEmail());
        updateUser.setFirstName(user.getFirstName());
        updateUser.setLastName(user.getLastName());

        // Updating the user in the database
        return userDao.save(updateUser);
    }
}
