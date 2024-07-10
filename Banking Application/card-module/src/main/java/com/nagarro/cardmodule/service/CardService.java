package com.nagarro.cardmodule.service;

import com.nagarro.cardmodule.entity.Card;
import com.nagarro.cardmodule.request.ActivationStatusRequest;

public interface CardService {
    Card issueCard(Card card);
    Card activateOrDeactivateCard(ActivationStatusRequest activationStatusRequest);
}
