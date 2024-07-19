package com.nagarro.loanmodule.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Loan {
    @Id
    @GeneratedValue(generator = "loan-id-generator")
    @GenericGenerator(name = "loan-id-generator",strategy = "com.nagarro.loanmodule.entity.LoanIdGenerator")
    private String loanId;
    private String email;
    private String accountNumber;
    private int tenure;
    private String mobile;
    private String loanType;
    private String loanStatus;
    private String verificationStatus;
    @Lob
    @Column(name = "document", columnDefinition = "MEDIUMBLOB")
    private byte[] document;
    private double loanAmount;
    private double rateOfInterest = 12.0;
    private LocalDateTime timestamp;
    private double emi;

}
