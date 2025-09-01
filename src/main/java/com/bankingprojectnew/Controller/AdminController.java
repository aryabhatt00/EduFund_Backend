package com.bankingprojectnew.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import com.bankingprojectnew.DTO.AdminAccountDTO;
import com.bankingprojectnew.DTO.AdminCustomerDTO;
import com.bankingprojectnew.DTO.AdminTransactionDTO;
import com.bankingprojectnew.Entity.BankTransaction;
import com.bankingprojectnew.Repository.AccountRepository;
import com.bankingprojectnew.Repository.BankTransactionRepository;
import com.bankingprojectnew.Repository.CustomerRepository;
import com.bankingprojectnew.Service.BankingService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BankTransactionRepository bankTransactionRepository;

    @Autowired
    private BankingService bankingService;


    @GetMapping("/customers")
    public List<AdminCustomerDTO> getAllCustomers() {
        return customerRepository.getAllAdminCustomers();
    }

    @GetMapping("/accounts")
    public List<AdminAccountDTO> getAllAccounts() {
        return accountRepository.getAllAdminAccounts();
    }


    @GetMapping("/transactions")
    public List<AdminTransactionDTO> getAllTransactions() {
        List<BankTransaction> transactions = bankTransactionRepository.findAll();

        return transactions.stream().map(tx -> new AdminTransactionDTO(
            tx.getTransactionId(),
            tx.getTransactionType().toString(),
            tx.getTransactionAmount(),
            tx.getBalanceAfterTransaction(),
            tx.getTransactionDate(),
            tx.getTransactionAccount().getAccountNumber(),
            (tx.getTransactionAccount().getCustomer() != null) ? tx.getTransactionAccount().getCustomer().getCustomerName() : "N/A"
        )).collect(Collectors.toList());
    }
    
    @DeleteMapping("/account/{accountNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountNumber) {
        boolean deleted = bankingService.deleteAccountByNumber(accountNumber);
        if (deleted) {
            return ResponseEntity.ok("Account deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }


}
