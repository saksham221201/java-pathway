package com.nagarro.kycmodule.dao;

import com.nagarro.kycmodule.entity.Kyc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KycDao extends JpaRepository<Kyc,Integer> {
}
