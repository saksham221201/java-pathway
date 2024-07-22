package com.nagarro.loanmodule.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class VerifyStatusRequest {
    private String verifyStatus;
    private String loanId;
}
