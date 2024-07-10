package com.nagarro.cardmodule.entity;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.security.SecureRandom;

public class CustomIdGenerator implements IdentifierGenerator {

    private static final SecureRandom random = new SecureRandom();

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        String first8Digits = "44654201";
        return generateCustomId(first8Digits);
    }

    private String generateCustomId(String first8Digits){
        StringBuilder sb = new StringBuilder(first8Digits);
        for (int i = 0;i<8;i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
