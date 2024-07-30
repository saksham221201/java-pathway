package com.nagarro.kycmodule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hc.client5.http.utils.Base64;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kyc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String kycStatus;
    private String userId;

    @Lob
    @Column(name = "document", columnDefinition = "MEDIUMBLOB")
    private byte[] document;

    public String getImageDataBase64() {
        return Base64.encodeBase64String(this.document);
    }

}
