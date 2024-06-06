package com.nagarro.usermodule.service.impl;

import com.nagarro.usermodule.dao.UserDao;
import com.nagarro.usermodule.entity.User;
import com.nagarro.usermodule.exception.EmptyInputException;
import com.nagarro.usermodule.exception.RecordAlreadyExistsException;
import com.nagarro.usermodule.exception.RecordNotFoundException;
import com.nagarro.usermodule.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User addUser(User user) {

        logger.info("Inside Add Users");
        logger.debug("Inside Add Users");

        // Checking if any of the fields is Empty or not
        if(user.getEmail().isBlank() || user.getFirstName().isBlank() || user.getLastName().isBlank() || user.getPassword().isBlank()){
            logger.error("Inputs are blank in addUser");
            throw new EmptyInputException("Input cannot be null!!", HttpStatus.BAD_REQUEST.value());
        }

        // Checking if the user with the given email already exists or not
        Optional<User> optionalUser = userDao.findByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            logger.error("User already exists");
            throw new RecordAlreadyExistsException("User already exists with email: " + user.getEmail(), HttpStatus.BAD_REQUEST.value());
        }

        // Encrypting the Password
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        logger.info("Password is encrypted");

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
            logger.error("User not found with id:{}", userId);
            throw new RecordNotFoundException("User not found with id: " + userId, HttpStatus.NOT_FOUND.value());
        }

        return optionalUser.get();
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> optionalUser = userDao.findByEmail(email);
        if(optionalUser.isEmpty()){
            logger.error("User not found with email:{}", email);
            throw new RecordNotFoundException("User not found with email: " + email, HttpStatus.NOT_FOUND.value());
        }
        return optionalUser.get();
    }

    @Override
    public User updateUser(Long userId, User user) {
        // Checking if any of the inputs is null
        if(user.getEmail().isBlank() || user.getFirstName().isBlank() || user.getLastName().isBlank() || user.getPassword().isBlank()){
            logger.error("Inputs are blank in updateUser");
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

        // Encrypting the Password
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        updateUser.setPassword(encryptedPassword);

        logger.info("User updated Successfully");

        // Updating the user in the database
        return userDao.save(updateUser);
    }

    @Override
    public void deleteUser(Long userId) {
        // Checking if the user with the given id already exists or not
        Optional<User> existingUser = userDao.findById(userId);
        if(existingUser.isEmpty()){
            logger.error("User not found with userId:{}", userId);
            throw new RecordNotFoundException("User not found with id: " + userId, HttpStatus.NOT_FOUND.value());
        }

        userDao.deleteById(userId);
    }
}
