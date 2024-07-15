package com.nagarro.cardmodule.service.impl;

import com.nagarro.cardmodule.client.AccountClient;
import com.nagarro.cardmodule.client.UserClient;
import com.nagarro.cardmodule.dao.CardDao;
import com.nagarro.cardmodule.dto.AccountDTO;
import com.nagarro.cardmodule.dto.User;
import com.nagarro.cardmodule.entity.Card;
import com.nagarro.cardmodule.exception.BadRequestException;
import com.nagarro.cardmodule.exception.EmptyInputException;
import com.nagarro.cardmodule.exception.RecordNotFoundException;
import com.nagarro.cardmodule.request.ActivationStatusRequest;
import com.nagarro.cardmodule.request.ReportLostCardRequest;
import com.nagarro.cardmodule.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

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

        logger.info("This is account Number: {}", card.getAccountNumber());

        AccountDTO accountDTO = accountClient.getAccountDetailsByAccountNumber(card.getAccountNumber());
        if (!accountDTO.getEmail().equals(card.getEmail())) {
            throw new BadRequestException("Invalid Email for userId", HttpStatus.BAD_REQUEST.value());
        }
        logger.info("Account is {}", accountDTO);

        User userDetails = userClient.getUserByEmail(card.getEmail());
        String fullName = userDetails.getFirstName() + " " + userDetails.getLastName();
        if (!fullName.equalsIgnoreCase(card.getName())) {
            throw new BadRequestException("Name is not valid, please enter full Name", HttpStatus.BAD_REQUEST.value());
        }

        card.setExpiryDate(LocalDate.now().plusYears(10));

        // Encrypting the Password
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedCvv = bCryptPasswordEncoder.encode(card.getCvv());
        card.setCvv(encryptedCvv);
        logger.info("Hashed: " + encryptedCvv);
        logger.info("CVV is encrypted inside Issue Card");

        return cardDao.save(card);
    }

    @Override
    public Card activateOrDeactivateCard(ActivationStatusRequest activationStatusRequest) {
        logger.debug("Inside activate card");

        AccountDTO accountDTO = accountClient.getAccountDetailsByAccountNumber(activationStatusRequest.getAccountNumber());
        if (!accountDTO.getEmail().equals(activationStatusRequest.getEmail())) {
            throw new BadRequestException("Invalid Email for userId", HttpStatus.BAD_REQUEST.value());
        }

        Optional<Card> optionalCard = cardDao.findById(activationStatusRequest.getCardNumber());
        if (optionalCard.isEmpty()) {
            throw new BadRequestException("Card does not exist", HttpStatus.BAD_REQUEST.value());
        }
        Card card = optionalCard.get();
        logger.debug("Card Status Before: {}", card.isActivationStatus());
        card.setActivationStatus(activationStatusRequest.isActivationStatus());
        logger.debug("Card Status After: {}", card.isActivationStatus());
        return card;
    }

    @Override
    public Card reportLostCard(ReportLostCardRequest reportLostCardRequest) {
        AccountDTO accountDTO = accountClient.getAccountDetailsByAccountNumber(reportLostCardRequest.getAccountNumber());
        if (!accountDTO.getEmail().equals(reportLostCardRequest.getEmail())) {
            throw new BadRequestException("Invalid Email for userId", HttpStatus.BAD_REQUEST.value());
        }

        Optional<Card> optionalCard = cardDao.findById(reportLostCardRequest.getCardNumber());
        if (optionalCard.isEmpty()) {
            throw new RecordNotFoundException("Card not found with card number: " + reportLostCardRequest.getCardNumber(), HttpStatus.NOT_FOUND.value());
        }
        Card card = optionalCard.get();
        card.setLost(true);
        card.setActivationStatus(false);
        return cardDao.save(card);
    }

    public Card getCardDetails(String cardNumber){
        Card card = cardDao.findById(cardNumber).orElseThrow(() -> new RecordNotFoundException("No card found for cardNumber",HttpStatus.BAD_REQUEST.value()));

        return card;
    }
}
