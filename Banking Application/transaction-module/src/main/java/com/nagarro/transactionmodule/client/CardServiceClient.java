package com.nagarro.transactionmodule.client;

import com.nagarro.transactionmodule.dto.CardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CARD-MODULE")
public interface CardServiceClient {
    @GetMapping("/v1/api/cards/{cardNumber}")
    public ResponseEntity<CardDTO> getCardDetails(@PathVariable String cardNumber);
}
