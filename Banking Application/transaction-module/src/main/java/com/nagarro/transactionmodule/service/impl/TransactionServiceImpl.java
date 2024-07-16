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

    @Autowired
    private AccountServiceClient accountServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private CardServiceClient cardServiceClient;

    @Autowired
    private MailService mailService;

    @Autowired
    private TransactionDao transactionDao;

    @Override
    @Transactional
    public AccountDTO depositMoney(TransactionRequest transactionRequest) {
        logger.debug("Inside deposit Money");

        AccountDTO accountDTO = accountServiceClient.getAccountDetailsByAccountNumber(transactionRequest.getAccountNumber());

        User user = userServiceClient.getUserByEmail(transactionRequest.getEmail());
        if (!accountDTO.getEmail().equals(transactionRequest.getEmail())) {
            logger.error("Invalid Account Details in deposit");
            throw new BadRequestException("Invalid Account Details", HttpStatus.BAD_REQUEST.value());
        }

        if (accountDTO.getAccountType().equalsIgnoreCase("FIXED")) {
            throw new BadRequestException("Cannot deposit to a FIXED Account", HttpStatus.BAD_REQUEST.value());
        }

        accountDTO.setBalance(accountDTO.getBalance() + transactionRequest.getAmount());
        logger.debug("Amount added");

        Mail mail = new Mail();
        mail.setSubject("Bank Transaction - Money Deposited");
        mail.setMessage("There was a transaction of Rs " + transactionRequest.getAmount() + ". Total Balance Available: " + accountDTO.getBalance());

        mailService.sendMail(transactionRequest.getEmail(), mail);

        accountServiceClient.updateBalance(transactionRequest.getAccountNumber(),accountDTO.getBalance());

        saveTransaction(transactionRequest,accountDTO.getBalance(),"DEPOSIT");
        logger.info("Deposit Transaction saved");

        return accountDTO;
    }

    @Override
    @Transactional
    public AccountDTO withdrawMoney(TransactionRequest transactionRequest) {
        logger.debug("Inside withdraw Money");
        AccountDTO accountDTO = accountServiceClient.getAccountDetailsByAccountNumber(transactionRequest.getAccountNumber());

        if (!accountDTO.getEmail().equals(transactionRequest.getEmail())) {
            logger.error("Invalid Account Details in withdraw money");
            throw new BadRequestException("Invalid Account Details", HttpStatus.BAD_REQUEST.value());
        }

        if (accountDTO.getAccountType().equalsIgnoreCase("FIXED")) {
            throw new BadRequestException("Cannot withdraw from FIXED Account", HttpStatus.BAD_REQUEST.value());
        }

        double initialBalance = accountDTO.getBalance();

        // Checking if the amount to be withdrawn is greater than the present balance or not
        if (initialBalance < transactionRequest.getAmount()) {
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

        Mail mail = new Mail();
        mail.setSubject("Bank Transaction - Money Withdraw");
        mail.setMessage("There was a transaction of Rs " + transactionRequest.getAmount() + ". Total Balance Available: " + accountDTO.getBalance());

        mailService.sendMail(transactionRequest.getEmail(), mail);

        accountServiceClient.updateBalance(transactionRequest.getAccountNumber(),accountDTO.getBalance());

        saveTransaction(transactionRequest,accountDTO.getBalance(),"WITHDRAW");
        logger.info("Withdrawal Transaction saved");
        return accountDTO;
    }

    @Override
    public List<Transaction> getTransactions(String accountNumber) {
        logger.debug("Inside Get Transactions by accountNumber");
        return transactionDao.findByAccountNumber(accountNumber);
    }

    @Override
    public AccountDTO transferMoney(TransferRequest transferRequest){
        logger.debug("Inside transfer Money");
        AccountDTO accountDTODebit = accountServiceClient.getAccountDetailsByAccountNumber(transferRequest.getAccountNumber());

        if (!accountDTODebit.getEmail().equals(transferRequest.getEmail())) {
            logger.error("Invalid Account Details in transfer money");
            throw new BadRequestException("Invalid Account Details", HttpStatus.BAD_REQUEST.value());
        }

        double initialBalance = accountDTODebit.getBalance();

        // Checking if the amount to be withdrawn is greater than the present balance or not
        if (initialBalance < transferRequest.getAmount()) {
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

        TransactionRequest req1 = new TransactionRequest();
        req1.setAmount(transferRequest.getAmount());
        req1.setAccountNumber(transferRequest.getAccountNumber());
        saveTransaction(req1,accountDTODebit.getBalance(),"TRANSFER WITHDRAWAL");
        logger.info("Transfer Withdrawal Transaction saved");

        // Transfer deposit
        TransactionRequest req2 = new TransactionRequest();

        AccountDTO accountDTOCredit = accountServiceClient.getAccountDetailsByAccountNumber(transferRequest.getCreditAccountNumber());
        accountDTOCredit.setBalance(accountDTOCredit.getBalance() + transferRequest.getAmount());
        logger.debug("Transfer money deposited");
        accountServiceClient.updateBalance(transferRequest.getCreditAccountNumber(),accountDTOCredit.getBalance());
        req2.setAmount(transferRequest.getAmount());
        req2.setAccountNumber(transferRequest.getCreditAccountNumber());
        saveTransaction(req2,accountDTOCredit.getBalance(),"TRANSFER DEPOSIT");
        logger.info("Transfer Deposit Transaction saved");

        return accountDTODebit;
    }

    @Override
    @Transactional
    public AccountDTO cardWithdrawal(CardTransaction cardTransaction) {
        logger.debug("Inside card withdraw");

        // Checking if any of the fields is Empty or not
        if(cardTransaction.getEmail().isBlank() || cardTransaction.getCardNumber().isBlank() || cardTransaction.getAccountNumber().isBlank() || cardTransaction.getCvv().isBlank()){
            logger.error("Inputs are blank in card withdrawal");
            throw new EmptyInputException("Input cannot be null!!", HttpStatus.BAD_REQUEST.value());
        }
        AccountDTO accountDTO = accountServiceClient.getAccountDetailsByAccountNumber(cardTransaction.getAccountNumber());

        if (!accountDTO.getEmail().equals(cardTransaction.getEmail())) {
            logger.error("Invalid Account Details in card withdrawal");
            throw new BadRequestException("Invalid Account Details", HttpStatus.BAD_REQUEST.value());
        }

        if (accountDTO.getAccountType().equalsIgnoreCase("FIXED")) {
            throw new BadRequestException("Cannot withdraw from FIXED Account", HttpStatus.BAD_REQUEST.value());
        }

        CardDTO cardDetails = cardServiceClient.getCardDetails(cardTransaction.getCardNumber()).getBody();
        logger.info("CVV: {}", cardDetails.getCvv());
        logger.info("CardTransaction CVV: {}", cardTransaction.getCvv());

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        logger.debug(String.valueOf(bCryptPasswordEncoder.matches(cardTransaction.getCvv(), cardDetails.getCvv())));
        if (!bCryptPasswordEncoder.matches(cardTransaction.getCvv(), cardDetails.getCvv())) {
            throw new BadRequestException("CVV is not matching", HttpStatus.BAD_REQUEST.value());
        }


        // Checking for Daily limit of the CARD
        List<Transaction> transactions = getTransactions(cardTransaction.getAccountNumber());
        logger.info(transactions.toString());
        double sum = transactions.stream()
                .filter(transaction -> LocalDate.now().equals(transaction.getTimestamp()
                        .toLocalDate()) && "CARD WITHDRAWAL"
                        .equals(transaction.getTransactionType()))
                .mapToDouble(Transaction::getAmount).sum();

        logger.info(String.valueOf(sum));

        if (sum + cardTransaction.getAmount() > cardDetails.getDailyLimit()) {
            throw new LimitExceededException("Daily limit exceeded", HttpStatus.FORBIDDEN.value());
        }

        double initialBalance = accountDTO.getBalance();

        // Checking if the amount to be withdrawn is greater than the present balance or not
        if (initialBalance < cardTransaction.getAmount()) {
            logger.error("Not enough Balance for card withdrawal!! Cannot withdraw money");
            throw new InsufficientBalanceException("Not enough balance for card withdrawal!! Cannot withdraw money",
                    HttpStatus.BAD_REQUEST.value());
        }

        // Checking if the amount to be withdrawn in the account is valid or not
        if (cardTransaction.getAmount() <= 0) {
            logger.error("Please enter valid Amount in card withdrawal");
            throw new BadRequestException("Please enter valid Amount", HttpStatus.BAD_REQUEST.value());
        }

        accountDTO.setBalance(accountDTO.getBalance() - cardTransaction.getAmount());

        Mail mail = new Mail();
        mail.setSubject("Bank Transaction - Money Withdrawn from card");
        mail.setMessage("There was a transaction of Rs " + cardTransaction.getAmount() + ". Total Balance Available: " + accountDTO.getBalance());

        mailService.sendMail(cardTransaction.getEmail(), mail);

        accountServiceClient.updateBalance(cardTransaction.getAccountNumber(),accountDTO.getBalance());

        TransactionRequest req = new TransactionRequest();
        req.setAmount(cardTransaction.getAmount());
        req.setEmail(cardTransaction.getEmail());
        req.setAccountNumber(cardTransaction.getAccountNumber());

        saveTransaction(req,accountDTO.getBalance(),"CARD WITHDRAWAL");
        logger.info("Card Withdrawal Transaction saved");
        return accountDTO;
    }

    private void saveTransaction(TransactionRequest req, Double balance, String transactionType){
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionType);
        transaction.setAmount(req.getAmount());
        transaction.setAccountNumber(req.getAccountNumber());
        transaction.setBalance(balance);
        transaction.setTimestamp(LocalDateTime.now());

        transactionDao.save(transaction);
    }

    private static boolean isRecentTransaction(Transaction transaction) {
        // date is stored like this: `2022-05-16 20:54:48.110`
        // we need the first part representing the current date in accordance with ISO-8601
        String date = String.valueOf(transaction.getTimestamp().toLocalDate());
        return LocalDate.now().isEqual(LocalDate.parse(date));
    }
}