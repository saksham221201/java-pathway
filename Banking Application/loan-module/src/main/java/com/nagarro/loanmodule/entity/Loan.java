package com.nagarro.loanmodule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    private String accountNumber;
    private String loanStatus;
    private int tenure;
    private String loanType;
    private int emi;
    private String verificationStatus;

    @Lob
    @Column(name = "document", columnDefinition = "MEDIUMBLOB")
    private byte[] document;
}
