package com.nagarro.loanmodule.entity;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.security.SecureRandom;

public class LoanIdGenerator implements IdentifierGenerator {

    private static final SecureRandom random = new SecureRandom();

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        String first5Digits = "12345";
        return generateCustomId(first5Digits);
    }

    private String generateCustomId(String first5Digits){
        StringBuilder sb = new StringBuilder(first5Digits);
        for (int i = 0;i<6;i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}

