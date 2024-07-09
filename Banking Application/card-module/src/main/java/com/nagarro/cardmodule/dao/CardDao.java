package com.nagarro.cardmodule.dao;

import com.nagarro.cardmodule.entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardDao extends JpaRepository<Cards,Long> {
}
