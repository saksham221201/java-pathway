package com.nagarro.cardmodule.dao;

import com.nagarro.cardmodule.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardDao extends JpaRepository<Card,String> {
}
