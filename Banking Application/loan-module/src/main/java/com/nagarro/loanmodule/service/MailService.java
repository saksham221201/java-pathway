package com.nagarro.loanmodule.service;

import com.nagarro.loanmodule.dto.Mail;

public interface MailService {
    void sendMail(String recipientMail, Mail mail);
}
