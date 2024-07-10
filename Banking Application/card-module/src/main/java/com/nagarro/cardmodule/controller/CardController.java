package com.nagarro.cardmodule.controller;

import com.nagarro.cardmodule.entity.Card;
import com.nagarro.cardmodule.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/issue")
    public ResponseEntity<Card> issueCard(@RequestBody Card card) {
        Card issuedCard = cardService.issueCard(card);
        return new ResponseEntity<>(issuedCard, HttpStatus.CREATED);
    }
}
