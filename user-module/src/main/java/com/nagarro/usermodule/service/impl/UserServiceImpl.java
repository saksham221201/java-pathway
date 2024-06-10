package com.nagarro.usermodule.service.impl;

import com.nagarro.usermodule.dao.UserDao;
import com.nagarro.usermodule.entity.Role;
import com.nagarro.usermodule.entity.User;
import com.nagarro.usermodule.exception.EmptyInputException;
import com.nagarro.usermodule.exception.RecordAlreadyExistsException;
import com.nagarro.usermodule.exception.RecordNotFoundException;
import com.nagarro.usermodule.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
   // @PreAuthorize("hasRole(T(com.nagarro.usermodule.entity.Role).ADMIN.name()) or hasRole(T(com.nagarro.usermodule.entity.Role).USER.name())")
    public User addUser(User user) {

        logger.debug("Inside Add Users");

        // Checking if any of the fields is Empty or not
        if(user.getEmail().isBlank() || user.getFirstName().isBlank() || user.getLastName().isBlank() || user.getPassword().isBlank() || user.getMobile().isBlank()){
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
        logger.info("Password is encrypted inside Add users");
        user.setRole(Role.USER);
        logger.debug("User is saved");
        // Saving user to database
        return userDao.save(user);
    }

//    @PreAuthorize("hasRole(T(com.nagarro.usermodule.entity.Role).ADMIN.name()")

    public User addAdmin(User user){
        logger.debug("Inside Add Admin");

        // Checking if any of the fields is Empty or not
        if(user.getEmail().isBlank() || user.getFirstName().isBlank() || user.getLastName().isBlank() || user.getPassword().isBlank() || user.getMobile().isBlank()){
            logger.error("Inputs are blank in addAdmin");
            throw new EmptyInputException("Input cannot be null!!", HttpStatus.BAD_REQUEST.value());
        }

        // Checking if the user with the given email already exists or not
        Optional<User> optionalUser = userDao.findByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            logger.error("Admin already exists");
            throw new RecordAlreadyExistsException("Admin already exists with email: " + user.getEmail(), HttpStatus.BAD_REQUEST.value());
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        logger.info("Password is encrypted inside Add Admin");
        user.setRole(Role.ADMIN);
        logger.debug("Admin is saved");

        return userDao.save(user);

    }

    @Override
    //@PreAuthorize("hasRole(T(com.nagarro.usermodule.entity.Role).ADMIN.name())")
    public List<User> getAllUsers() {
        logger.debug("Inside getting all users");
        return userDao.findAll();
    }

    @Override
    //@PreAuthorize("hasRole(T(com.nagarro.usermodule.entity.Role).ADMIN.name()) or hasRole(T(com.nagarro.usermodule.entity.Role).USER.name())")
    public User getUserById(Long userId) {

        logger.debug("Inside getting user by userId");

        // Checking if the user with the given userId already exists or not
        Optional<User> optionalUser = userDao.findById(userId);
        if(optionalUser.isEmpty()){
            logger.error("User not found with id:{} inside getUserById", userId);
            throw new RecordNotFoundException("User not found with id: " + userId, HttpStatus.NOT_FOUND.value());
        }

        return optionalUser.get();
    }

    @Override
   // @PreAuthorize("hasRole(T(com.nagarro.usermodule.entity.Role).ADMIN.name()) or hasRole(T(com.nagarro.usermodule.entity.Role).USER.name())")
    public User getUserByEmail(String email) {

        logger.debug("Inside getting user by email");

        Optional<User> optionalUser = userDao.findByEmail(email);
        if(optionalUser.isEmpty()){
            logger.error("User not found with email:{}", email);
            throw new RecordNotFoundException("User not found with email: " + email, HttpStatus.NOT_FOUND.value());
        }
        return optionalUser.get();
    }

    @Override
   // @PreAuthorize("hasRole(T(com.nagarro.usermodule.entity.Role).ADMIN.name()) or hasRole(T(com.nagarro.usermodule.entity.Role).USER.name())")
    public User updateUser(Long userId, User user) {
        logger.debug("Inside update User");

        // Checking if any of the inputs is null
        if(user.getEmail().isBlank() || user.getFirstName().isBlank() || user.getLastName().isBlank() || user.getPassword().isBlank() || user.getMobile().isBlank()){
            logger.error("Inputs are blank in updateUser");
            throw new EmptyInputException("Input cannot be null", HttpStatus.BAD_REQUEST.value());
        }

        // Checking if the user with the given id already exists or not
        Optional<User> existingUser = userDao.findById(userId);
        if(existingUser.isEmpty()){
            logger.error("User not found with id:{} inside updateUser", userId);
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
        logger.info("Password is encrypted inside update Users");

        logger.info("User updated Successfully");

        // Updating the user in the database
        return userDao.save(updateUser);
    }

    @Override
   // @PreAuthorize("hasRole(T(com.nagarro.usermodule.entity.Role).ADMIN.name()) or hasRole(T(com.nagarro.usermodule.entity.Role).USER.name())")
    public void deleteUser(Long userId) {
        logger.debug("Inside Delete Users");

        // Checking if the user with the given id already exists or not
        Optional<User> existingUser = userDao.findById(userId);
        if(existingUser.isEmpty()){
            logger.error("User not found with userId:{} inside deleteUser", userId);
            throw new RecordNotFoundException("User not found with id: " + userId, HttpStatus.NOT_FOUND.value());
        }

        logger.info("User deleted");
        userDao.deleteById(userId);
    }
}
