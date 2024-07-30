package com.nagarro.kycmodule.response;

import com.nagarro.kycmodule.dto.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class KycDocumentResponse {
    private User user;
    private String documentText;
}
