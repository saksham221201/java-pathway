package com.nagarro.accountmodule.dao;

import com.nagarro.accountmodule.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao extends JpaRepository<Account, Long> {
}
