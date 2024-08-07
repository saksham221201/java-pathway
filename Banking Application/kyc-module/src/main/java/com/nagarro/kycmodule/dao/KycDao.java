package com.nagarro.kycmodule.dao;

import com.nagarro.kycmodule.entity.Kyc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KycDao extends JpaRepository<Kyc,Integer> {
    Optional<Kyc> findByEmail(String email);
}
