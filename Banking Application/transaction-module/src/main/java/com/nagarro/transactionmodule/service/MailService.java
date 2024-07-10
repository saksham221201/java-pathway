package com.nagarro.transactionmodule.service;

import com.nagarro.transactionmodule.dto.Mail;

public interface MailService {
    void sendMail(String recipientMail, Mail mail);
}
