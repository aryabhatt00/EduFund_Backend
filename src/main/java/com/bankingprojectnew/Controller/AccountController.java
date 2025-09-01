package com.bankingprojectnew.Controller;

import com.bankingprojectnew.Entity.Account;
import com.bankingprojectnew.Repository.AccountRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    // Create a new account
    @PostMapping("/create")
    public String createAccount(@RequestBody @Valid Account account) {
       
        Random random = new Random();
        long generatedAccountNumber = 1000000000L + random.nextInt(900000000);
        account.setAccountNumber(generatedAccountNumber);

        // Save to database
        accountRepository.save(account);

        return "Account created with AccountNumber: " + account.getAccountNumber();
    }


    // Get account by ID
    @GetMapping("/{id}")
    public Object getAccount(@PathVariable int id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // Get all accounts
    @GetMapping("/all")
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
