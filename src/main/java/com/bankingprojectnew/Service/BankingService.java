package com.bankingprojectnew.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankingprojectnew.Entity.Account;
import com.bankingprojectnew.Entity.BankTransaction;
import com.bankingprojectnew.Entity.BankTransactionType;
import com.bankingprojectnew.Entity.OtpRecord;
import com.bankingprojectnew.Repository.AccountRepository;
import com.bankingprojectnew.Repository.BankTransactionRepository;
import com.bankingprojectnew.Repository.CustomerRepository;

@Service
public class BankingService {

    @Autowired 
    private AccountRepository accountRepository;

    @Autowired
    private BankTransactionRepository bankTransactionRepository;

    @Transactional
    public String deposit(long accountNumber, double amount) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);

        if (optionalAccount.isEmpty()) {
            throw new IllegalArgumentException("Account not found");
        }

        Account account = optionalAccount.get();
        account.setAccountBalance(account.getAccountBalance() + amount);
        accountRepository.save(account);

        BankTransaction transaction = new BankTransaction();
        transaction.setBalanceAfterTransaction(account.getAccountBalance());

        transaction.setTransactionType(BankTransactionType.DEPOSIT);
        transaction.setTransactionAmount(amount);
        transaction.setTransactionDate(new Date());  
        transaction.setTransactionAccount(account);
        bankTransactionRepository.save(transaction);

        return "Deposit successful. New balance: $" + account.getAccountBalance();
    }

    @Transactional
    public String withdraw(long accountNumber, double amount) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);

        if (optionalAccount.isEmpty()) {
            throw new IllegalArgumentException("Account not found");
        }

        Account account = optionalAccount.get();
        if (account.getAccountBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance. Cannot withdraw $" + amount);
        }

        account.setAccountBalance(account.getAccountBalance() - amount);
        accountRepository.save(account);

        BankTransaction transaction = new BankTransaction();
        transaction.setBalanceAfterTransaction(account.getAccountBalance());

        transaction.setTransactionType(BankTransactionType.WITHDRAWAL);
        transaction.setTransactionAmount(amount);
        transaction.setTransactionDate(new Date());  
        transaction.setTransactionAccount(account);
        bankTransactionRepository.save(transaction);

        return "Withdraw successful. New balance: $" + account.getAccountBalance();
    }
    
    public Account getAccountByAccountNumber(long accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new NoSuchElementException("Account not found"));
    }
    
    
    
    @Service
    public class OtpService {
        private final JavaMailSender mailSender;
        private final Map<String, OtpRecord> otpStorage = new ConcurrentHashMap<>();

        public OtpService(JavaMailSender mailSender) {
            this.mailSender = mailSender;
        }

        public void sendOtp(String email, String type, double amount) {
            String otp = String.format("%06d", new Random().nextInt(999999));
            OtpRecord record = new OtpRecord(email, otp, type, amount, LocalDateTime.now().plusMinutes(5), false);
            otpStorage.put(email, record);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Your OTP for " + type);
            message.setText("Your OTP is: " + otp + " (valid for 5 minutes)");
            mailSender.send(message);
        }

        public boolean verifyOtp(String email, String otpInput) {
            OtpRecord record = otpStorage.get(email);
            if (record != null && !record.isVerified()
                && record.getOtp().equals(otpInput)
                && record.getExpiryTime().isAfter(LocalDateTime.now())) {
                record.setVerified(true);
                return true;
            }
            return false;
        }

        public OtpRecord getVerifiedRecord(String email) {
            OtpRecord record = otpStorage.get(email);
            return (record != null && record.isVerified()) ? record : null;
        }
    }

    @Autowired
    private CustomerRepository customerRepository;
    public boolean deleteAccountByNumber(long accountNumber) {
        Optional<Account> accountOpt = accountRepository.findByAccountNumber(accountNumber);
        
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();

            // Delete all transactions related to this account
            bankTransactionRepository.deleteByTransactionAccount(account);

            // Remove customer if exists
            if (account.getCustomer() != null) {
                customerRepository.delete(account.getCustomer());
            }

            // Delete the account last
            accountRepository.delete(account);
            return true;
        }

        return false;
    }




}
