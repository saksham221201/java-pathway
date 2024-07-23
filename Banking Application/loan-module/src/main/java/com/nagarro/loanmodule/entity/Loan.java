package com.nagarro.loanmodule.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hc.client5.http.utils.Base64;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Loan {
    @Id
    @GeneratedValue(generator = "loan-id-generator")
    @GenericGenerator(name = "loan-id-generator",strategy = "com.nagarro.loanmodule.entity.LoanIdGenerator")
    private String loanId;
    private String email;
    private String accountNumber;
    private int tenure;
    private String mobile;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    private String loanStatus;
    private String verificationStatus;

    @Lob
    @Column(name = "document", columnDefinition = "MEDIUMBLOB")
    private byte[] document;

    public String getImageDataBase64() {
        return Base64.encodeBase64String(this.document);
    }

    private double loanAmount;
    private double rateOfInterest;
    private LocalDateTime timestamp;
    private double emi;

}
