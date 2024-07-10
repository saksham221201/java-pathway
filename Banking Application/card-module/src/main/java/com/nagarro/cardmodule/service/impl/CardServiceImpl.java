package com.nagarro.cardmodule.service.impl;

import com.nagarro.cardmodule.client.AccountClient;
import com.nagarro.cardmodule.client.UserClient;
import com.nagarro.cardmodule.dao.CardDao;
import com.nagarro.cardmodule.dto.AccountDTO;
import com.nagarro.cardmodule.dto.User;
import com.nagarro.cardmodule.entity.Card;
import com.nagarro.cardmodule.exception.BadRequestException;
import com.nagarro.cardmodule.exception.EmptyInputException;
import com.nagarro.cardmodule.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

    private final Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);

    @Autowired
    private CardDao cardDao;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private UserClient userClient;

    @Override
    public Card issueCard(Card card) {

        logger.debug("Inside issue card");

        // Checking if any of the fields is Empty
        if (card.getName().isEmpty()) {
            logger.error("Inputs are blank in issue card");
            throw new EmptyInputException("Input cannot be Null!", HttpStatus.BAD_REQUEST.value());
        }

        logger.info("This is account Number: {}", card.getAccountNo());

        AccountDTO accountDTO = accountClient.getAccountDetailsByAccountNumber(card.getAccountNo());
        if (!accountDTO.getEmail().equals(card.getEmail())) {
            throw new BadRequestException("Invalid Email for userId", HttpStatus.BAD_REQUEST.value());
        }
        logger.info("Account is {}", accountDTO);

        User userDetails = userClient.getUserByEmail(card.getEmail());
        String fullName = userDetails.getFirstName() + " " + userDetails.getLastName();
        if (!fullName.equalsIgnoreCase(card.getName())) {
            throw new BadRequestException("Name is not valid, please enter full Name", HttpStatus.BAD_REQUEST.value());
        }

        String cvvString = String.valueOf(card.getCvv());
        // Encrypting the Password
         BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
         String encryptedCvv = bCryptPasswordEncoder.encode(card.getCvv());
         card.setCvv(encryptedCvv);
         logger.info("Hashed: " + encryptedCvv);
         logger.info("CVV is encrypted inside Issue Card");

        return cardDao.save(card);
    }

    @Override
    public Card activateOrDeactivateCard(boolean request) {
        return null;
    }
}
