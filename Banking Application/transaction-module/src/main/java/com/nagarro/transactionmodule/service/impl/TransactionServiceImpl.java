package com.nagarro.transactionmodule.service.impl;

import com.nagarro.transactionmodule.client.CardServiceClient;
import com.nagarro.transactionmodule.dao.TransactionDao;
import com.nagarro.transactionmodule.dto.AccountDTO;
import com.nagarro.transactionmodule.dto.CardDTO;
import com.nagarro.transactionmodule.dto.Mail;
import com.nagarro.transactionmodule.dto.User;
import com.nagarro.transactionmodule.entity.Transaction;
import com.nagarro.transactionmodule.exception.BadRequestException;
import com.nagarro.transactionmodule.exception.EmptyInputException;
import com.nagarro.transactionmodule.exception.InsufficientBalanceException;
import com.nagarro.transactionmodule.exception.LimitExceededException;
import com.nagarro.transactionmodule.request.CardTransaction;
import com.nagarro.transactionmodule.request.TransactionRequest;
import com.nagarro.transactionmodule.request.TransferRequest;
import com.nagarro.transactionmodule.client.AccountServiceClient;
import com.nagarro.transactionmodule.service.MailService;
import com.nagarro.transactionmodule.service.TransactionService;
import com.nagarro.transactionmodule.client.UserServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final AccountServiceClient accountServiceClient;
    private final UserServiceClient userServiceClient;
    private final CardServiceClient cardServiceClient;
    private final MailService mailService;
    private final TransactionDao transactionDao;

    @Autowired
    public TransactionServiceImpl(AccountServiceClient accountServiceClient, UserServiceClient userServiceClient, CardServiceClient cardServiceClient, MailService mailService, TransactionDao transactionDao) {
        this.accountServiceClient = accountServiceClient;
        this.userServiceClient = userServiceClient;
        this.cardServiceClient = cardServiceClient;
        this.mailService = mailService;
        this.transactionDao = transactionDao;
    }

    @Override
    @Transactional
    public AccountDTO withdrawMoney(TransactionRequest transactionRequest) {
        final AccountDTO accountDTO = accountServiceClient.getAccountDetailsByAccountNumber(transactionRequest.getAccountNumber());

        if (!accountDTO.getEmail().equalsIgnoreCase(transactionRequest.getEmail())) {
            logger.error("Invalid Account Details in withdraw money");
            throw new BadRequestException("Invalid Account Details", HttpStatus.BAD_REQUEST.value());
        }

        if (accountDTO.getAccountType().equalsIgnoreCase("FIXED")) {
            throw new BadRequestException("Cannot withdraw from FIXED Account", HttpStatus.BAD_REQUEST.value());
        }

        // Checking if the amount to be withdrawn is greater than the present balance or not
        if (accountDTO.getBalance() < transactionRequest.getAmount()) {
            logger.error("Not enough Balance!! Cannot withdraw money");
            throw new InsufficientBalanceException("Not enough balance!! Cannot withdraw money",
                    HttpStatus.BAD_REQUEST.value());
        }

        // Checking if the amount to be withdrawn in the account is valid or not
        if (transactionRequest.getAmount() <= 0) {
            logger.error("Please enter valid Amount in withdraw money");
            throw new BadRequestException("Please enter valid Amount", HttpStatus.BAD_REQUEST.value());
        }

        accountDTO.setBalance(accountDTO.getBalance() - transactionRequest.getAmount());

        sendMail(transactionRequest.getAmount(), accountDTO.getBalance(), transactionRequest.getEmail(), "Money Withdrawn");

        accountServiceClient.updateBalance(transactionRequest.getAccountNumber(),accountDTO.getBalance());

        saveTransaction(transactionRequest,accountDTO.getBalance(),"WITHDRAW");
        logger.debug("Withdrawal Transaction saved");
        return accountDTO;
    }

    @Override
    @Transactional
    public AccountDTO depositMoney(TransactionRequest transactionRequest) {
        final AccountDTO accountDTO = accountServiceClient.getAccountDetailsByAccountNumber(transactionRequest.getAccountNumber());

        final User user = userServiceClient.getUserByEmail(transactionRequest.getEmail());
        if (!accountDTO.getEmail().equalsIgnoreCase(transactionRequest.getEmail())) {
            logger.error("Invalid Account Details in deposit. Email not matching: {} {}", accountDTO.getEmail(), transactionRequest.getEmail());
            throw new BadRequestException("Invalid Account Details", HttpStatus.BAD_REQUEST.value());
        }

        if (accountDTO.getAccountType().equalsIgnoreCase("FIXED")) {
            logger.error("Cannot deposit to Account Type: {}", accountDTO.getAccountType());
            throw new BadRequestException("Cannot deposit to a FIXED Account", HttpStatus.BAD_REQUEST.value());
        }
        accountDTO.setBalance(accountDTO.getBalance() + transactionRequest.getAmount());

        sendMail(transactionRequest.getAmount(), accountDTO.getBalance(), transactionRequest.getEmail(), "Money Deposited");

        accountServiceClient.updateBalance(transactionRequest.getAccountNumber(),accountDTO.getBalance());

        saveTransaction(transactionRequest,accountDTO.getBalance(),"DEPOSIT");
        return accountDTO;
    }

    @Override
    public List<Transaction> getTransactions(String accountNumber) {
        return transactionDao.findByAccountNumber(accountNumber);
    }

    @Override
    @Transactional
    public AccountDTO transferMoney(TransferRequest transferRequest){

        logger.info("Account Number: {}", transferRequest.getAccountNumber());

        final AccountDTO accountDTODebit = accountServiceClient.getAccountDetailsByAccountNumber(transferRequest.getAccountNumber());
        logger.info("Details are: {}", accountDTODebit);

        if (!accountDTODebit.getEmail().equals(transferRequest.getEmail())) {
            logger.error("Invalid Account Details in transfer money");
            throw new BadRequestException("Invalid Account Details", HttpStatus.BAD_REQUEST.value());
        }

        // Checking if the amount to be withdrawn is greater than the present balance or not
        if (accountDTODebit.getBalance() < transferRequest.getAmount()) {
            logger.error("Not enough Balance!! Cannot transfer money");
            throw new InsufficientBalanceException("Not enough balance!! Cannot transfer money",
                    HttpStatus.BAD_REQUEST.value());
        }

        // Checking if the amount to be transferred in the account is valid or not
        if (transferRequest.getAmount() <= 0) {
            logger.error("Please enter valid Amount in transfer money");
            throw new BadRequestException("Please enter valid Amount", HttpStatus.BAD_REQUEST.value());
        }

        accountDTODebit.setBalance(accountDTODebit.getBalance() - transferRequest.getAmount());

        accountServiceClient.updateBalance(transferRequest.getAccountNumber(),accountDTODebit.getBalance());

        TransactionRequest transactionRequestWithdrawal = TransactionRequest.builder()
                .amount(transferRequest.getAmount())
                .accountNumber(transferRequest.getAccountNumber())
                .build();
        saveTransaction(transactionRequestWithdrawal,accountDTODebit.getBalance(),"TRANSFER WITHDRAWAL");


        final AccountDTO accountDTOCredit = accountServiceClient.getAccountDetailsByAccountNumber(transferRequest.getCreditAccountNumber());

        accountDTOCredit.setBalance(accountDTOCredit.getBalance() + transferRequest.getAmount());
        accountServiceClient.updateBalance(transferRequest.getCreditAccountNumber(),accountDTOCredit.getBalance());

        // Transfer deposit
        TransactionRequest transactionRequestDeposit = TransactionRequest
                .builder()
                .amount(transferRequest.getAmount())
                .accountNumber(transferRequest.getCreditAccountNumber())
                .build();
       saveTransaction(transactionRequestDeposit,accountDTOCredit.getBalance(),"TRANSFER DEPOSIT");

        return accountDTODebit;
    }

    @Override
    @Transactional
    public AccountDTO cardWithdrawal(CardTransaction cardTransaction) {

        // Checking if any of the fields is Empty or not
        if(cardTransaction.getEmail().isBlank() || cardTransaction.getCardNumber().isBlank() || cardTransaction.getAccountNumber().isBlank() || cardTransaction.getCvv().isBlank()){
            logger.error("Inputs are blank in card withdrawal");
            throw new EmptyInputException("Input cannot be null!!", HttpStatus.BAD_REQUEST.value());
        }

        final AccountDTO accountDTO = accountServiceClient.getAccountDetailsByAccountNumber(cardTransaction.getAccountNumber());

        if (!accountDTO.getEmail().equalsIgnoreCase(cardTransaction.getEmail())) {
            logger.error("Invalid Account Details in card withdrawal");
            throw new BadRequestException("Invalid Account Details", HttpStatus.BAD_REQUEST.value());
        }

        if (accountDTO.getAccountType().equalsIgnoreCase("FIXED")) {
            throw new BadRequestException("Cannot withdraw from FIXED Account", HttpStatus.BAD_REQUEST.value());
        }

        final CardDTO cardDetails = cardServiceClient.getCardDetails(cardTransaction.getCardNumber()).getBody();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (!bCryptPasswordEncoder.matches(cardTransaction.getCvv(), cardDetails.getCvv())) {
            logger.error("CVV not matching: {}", cardTransaction.getCvv());
            throw new BadRequestException("CVV is not matching", HttpStatus.BAD_REQUEST.value());
        }

        // Checking for Daily limit of the CARD
        List<Transaction> transactions = getTransactions(cardTransaction.getAccountNumber());
        double sum = transactions.stream()
                .filter(transaction -> LocalDate.now().equals(transaction.getTimestamp()
                        .toLocalDate()) && "CARD WITHDRAWAL"
                        .equals(transaction.getTransactionType()))
                .mapToDouble(Transaction::getAmount).sum();

        if (sum + cardTransaction.getAmount() > cardDetails.getDailyLimit()) {
            throw new LimitExceededException("Daily limit exceeded", HttpStatus.FORBIDDEN.value());
        }

        // Checking if the amount to be withdrawn is greater than the present balance or not
        if (accountDTO.getBalance() < cardTransaction.getAmount()) {
            logger.error("Not enough Balance for card withdrawal!! Cannot withdraw money");
            throw new InsufficientBalanceException("Not enough balance for card withdrawal!! Cannot withdraw money",
                    HttpStatus.BAD_REQUEST.value());
        }

        // Checking if the amount to be withdrawn in the account is valid or not
        if (cardTransaction.getAmount() <= 0) {
            logger.error("The amount: {} entered is not valid ",cardTransaction.getAmount());
            throw new BadRequestException("Please enter valid Amount", HttpStatus.BAD_REQUEST.value());
        }

        accountDTO.setBalance(accountDTO.getBalance() - cardTransaction.getAmount());

        sendMail(cardTransaction.getAmount(), accountDTO.getBalance(), cardDetails.getEmail(), "Money Withdrawn from card");

        accountServiceClient.updateBalance(cardTransaction.getAccountNumber(),accountDTO.getBalance());

        TransactionRequest transactionRequest = TransactionRequest
                .builder()
                .amount(cardTransaction.getAmount())
                .email(cardTransaction.getEmail())
                .accountNumber(cardTransaction.getAccountNumber())
                .build();
        saveTransaction(transactionRequest,accountDTO.getBalance(),"CARD WITHDRAWAL");
        return accountDTO;
    }

    private void saveTransaction(TransactionRequest transactionRequest, Double balance, String transactionType){
        Transaction transaction = Transaction.builder()
                .transactionType(transactionType)
                .amount(transactionRequest.getAmount())
                .accountNumber(transactionRequest.getAccountNumber())
                .balance(balance)
                .timestamp(LocalDateTime.now())
                .build();
        transactionDao.save(transaction);
    }

    private void sendMail(double amount, double balance, String email, String transactionType) {
        Mail mail = new Mail();
        mail.setSubject("Bank Transaction - " + transactionType);
        mail.setMessage("There was a transaction of Rs " + amount + ". Total Balance Available: " + balance);

        mailService.sendMail(email, mail);
    }
}