package com.nagarro.usermodule.dao;

import com.nagarro.usermodule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
}
