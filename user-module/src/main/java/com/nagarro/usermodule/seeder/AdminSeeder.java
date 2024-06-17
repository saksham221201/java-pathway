package com.nagarro.usermodule.seeder;

import com.nagarro.usermodule.entity.Role;
import com.nagarro.usermodule.entity.User;
import com.nagarro.usermodule.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//Database Seeder is a class to HardCode the values for the 1st Admin
@Component
public class AdminSeeder {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void seedDatabase() {
        User adminUser = getHardcodedData();
        userService.seedAdmin(adminUser);
    }

    private User getHardcodedData() {
        return new User(1L, "Nagarro", "Nagarro", "nagarro@nagarro.com", "Nagarro22", "1234567890", Role.ADMIN);
    }
}
