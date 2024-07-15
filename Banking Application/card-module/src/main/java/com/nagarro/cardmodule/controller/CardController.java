package com.nagarro.cardmodule.controller;

import com.nagarro.cardmodule.entity.Card;
import com.nagarro.cardmodule.request.ActivationStatusRequest;
import com.nagarro.cardmodule.request.ReportLostCardRequest;
import com.nagarro.cardmodule.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/activate")
    public ResponseEntity<Card> activateOrDeactivateCard(@RequestBody ActivationStatusRequest request) {
        Card card = cardService.activateOrDeactivateCard(request);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @PostMapping("/reportLost")
    public ResponseEntity<Card> reportLostCard(@RequestBody ReportLostCardRequest reportLostCardRequest) {
        Card card = cardService.reportLostCard(reportLostCardRequest);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @GetMapping("/{cardNumber}")
    public ResponseEntity<Card> getCardDetails(@PathVariable String cardNumber) {
        Card card = cardService.getCardDetails(cardNumber);
        return new ResponseEntity<>(card,HttpStatus.OK);
    }

}
