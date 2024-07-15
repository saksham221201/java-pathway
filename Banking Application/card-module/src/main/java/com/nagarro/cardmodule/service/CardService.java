package com.nagarro.cardmodule.service;

import com.nagarro.cardmodule.entity.Card;
import com.nagarro.cardmodule.request.ActivationStatusRequest;
import com.nagarro.cardmodule.request.ReportLostCardRequest;

public interface CardService {
    Card issueCard(Card card);
    Card activateOrDeactivateCard(ActivationStatusRequest activationStatusRequest);
    Card reportLostCard(ReportLostCardRequest reportLostCardRequest);
    Card getCardDetails(String cardNumber);
}
