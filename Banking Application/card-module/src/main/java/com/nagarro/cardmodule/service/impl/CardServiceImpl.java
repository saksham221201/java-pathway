package com.nagarro.cardmodule.service.impl;

import com.nagarro.cardmodule.client.AccountClient;
import com.nagarro.cardmodule.dao.CardDao;
import com.nagarro.cardmodule.dto.AccountDTO;
import com.nagarro.cardmodule.entity.Card;
import com.nagarro.cardmodule.exception.BadRequestException;
import com.nagarro.cardmodule.exception.EmptyInputException;
import com.nagarro.cardmodule.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class CardServiceImpl implements CardService {

    private final Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);

    @Autowired
    private CardDao cardDao;

    @Autowired
    private AccountClient accountClient;

    @Override
    public Card issueCard(Card card) {

        logger.debug("Inside issue card");

        // Checking if any of the fields is Empty
        if (card.getName().isEmpty()) {
            logger.error("Inputs are blank in issue card");
            throw new EmptyInputException("Input cannot be Null!", HttpStatus.BAD_REQUEST.value());
        }

        AccountDTO accountDTO = accountClient.getAccountDetailsByAccountNumber(card.getAccountNo());
        if (!accountDTO.getEmail().equals(card.getEmail())) {
            throw new BadRequestException("Invalid Email for userId", HttpStatus.BAD_REQUEST.value());
        }
        logger.info("Account is {}", accountDTO);

        return cardDao.save(card);
    }

    @Override
    public Card activateOrDeactivateCard(boolean request) {
        return null;
    }
}
