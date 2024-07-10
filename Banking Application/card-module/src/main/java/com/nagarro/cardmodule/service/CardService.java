package com.nagarro.cardmodule.service;

import com.nagarro.cardmodule.entity.Card;

public interface CardService {
    Card issueCard(Card card);
    Card activateOrDeactivateCard(boolean request);
}
