package com.bankingprojectnew.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bankingprojectnew.DTO.OtpTransactionRequest;
import com.bankingprojectnew.DTO.OtpVerifyTransactionRequest;
import com.bankingprojectnew.DTO.TransactionHistoryDTO;
import com.bankingprojectnew.DTO.TransactionRequest;
import com.bankingprojectnew.Entity.Account;
import com.bankingprojectnew.Entity.BankTransaction;
import com.bankingprojectnew.Repository.BankTransactionRepository;
import com.bankingprojectnew.Service.BankingService;
import com.bankingprojectnew.Service.OtpService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/bank")
public class BankingController {

    @Autowired
    private BankingService bankingService;

    @Autowired
    private BankTransactionRepository bankTransactionRepository;

    @PostMapping("/deposit")
    public ResponseEntity<Map<String, Object>> deposit(@RequestBody Map<String, Object> payload) {
        long accountNumber = Long.parseLong(payload.get("accountNumber").toString());
        double amount = Double.parseDouble(payload.get("amount").toString());

        Map<String, Object> response = new HashMap<>();

        try {
            String newBalance = bankingService.deposit(accountNumber, amount);
            response.put("message", "Deposit Successful");
            response.put("depositAmount", amount);
            response.put("newBalance", newBalance);

            return ResponseEntity.ok(response);
        } catch (NoSuchElementException ex) {
            response.put("message", "Account not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception ex) {
            response.put("message", "Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Map<String, Object>> withdraw(@RequestBody Map<String, Object> payload) {
        long accountNumber = Long.parseLong(payload.get("accountNumber").toString());
        double amount = Double.parseDouble(payload.get("amount").toString());

        Map<String, Object> response = new HashMap<>();

        try {
            String newBalance = bankingService.withdraw(accountNumber, amount);
            response.put("message", "Withdrawal Successful");
            response.put("withdrawAmount", amount);
            response.put("newBalance", newBalance);
            return ResponseEntity.ok(response);

        } catch (NoSuchElementException ex) {
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (IllegalArgumentException ex) {
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (Exception ex) {
            response.put("message", "Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }



    @GetMapping("/test")
    public String test() {
        return "BankingController is working!";
    }
    @GetMapping("/transactions")
    public TransactionHistoryDTO getTransactions(@RequestParam long accountNumber) {
        Account account = bankingService.getAccountByAccountNumber(accountNumber);
        List<BankTransaction> transactions = bankTransactionRepository.findByTransactionAccount(account);

        List<TransactionHistoryDTO.TransactionEntry> transactionDTOs = transactions.stream()
            .map(tx -> new TransactionHistoryDTO.TransactionEntry(
                tx.getTransactionId(),
                tx.getTransactionType().toString(),
                tx.getTransactionAmount(),
                tx.getTransactionDate(),
                tx.getBalanceAfterTransaction(),
                tx.getTransactionAccount().getAccountNumber(),
                tx.getTransactionAccount().getCustomer().getCustomerName()
            ))
            .collect(Collectors.toList());

        return new TransactionHistoryDTO(
            account.getAccountNumber(),
            account.getAccountBalance(),
            transactionDTOs
        );
    }
    
    @Autowired
    private OtpService otpService;

    private Map<String, OtpTransactionRequest> pendingTransactions = new HashMap<>();

    @PostMapping("/request-otp")
    public ResponseEntity<Map<String, String>> requestOtp(@RequestBody OtpTransactionRequest request) {
        String email = request.getEmail();
        long accountNumber = request.getAccountNumber();

        // Basic null/blank check
        if (email == null || email.isBlank() || accountNumber <= 0) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email and account number are required"));
        }

        try {
            Account account = bankingService.getAccountByAccountNumber(accountNumber);

            // Check if account's customer email matches provided email
            if (!account.getCustomer().getCustomerEmail().equalsIgnoreCase(email)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Email does not match the account"));
            }

            // Store transaction intent before sending OTP
            String key = email + "_" + request.getOperation();
            pendingTransactions.put(key, request);

            otpService.sendOtp(email, request.getOperation());  // Now send OTP
            System.out.println(">> Storing OTP key: " + key);

            return ResponseEntity.ok(Map.of("message", "OTP sent to email"));

        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Account not found"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error processing request"));
        }
    }
    
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerifyTransactionRequest request) {
        String email = request.getEmail();
        String operation = request.getOperation();
        String providedOtp = request.getOtp();

        String key = email + "_" + operation;

        System.out.println(">> Verifying OTP for key: " + key);
        System.out.println(">> Received OTP: " + providedOtp);

        boolean verified = otpService.verifyOtp(email, operation, providedOtp);
        if (!verified) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "OTP verification failed"));
        }

        OtpTransactionRequest originalRequest = pendingTransactions.remove(key);
        if (originalRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "No pending transaction found"));
        }

        long accountNumber = originalRequest.getAccountNumber();
        double amount = originalRequest.getAmount();
        String type = originalRequest.getOperation();

        try {
            String newBalance;
            if ("deposit".equalsIgnoreCase(type)) {
                newBalance = bankingService.deposit(accountNumber, amount);
            } else if ("withdraw".equalsIgnoreCase(type)) {
                newBalance = bankingService.withdraw(accountNumber, amount);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Unknown operation type"));
            }

            return ResponseEntity.ok(Map.of(
                "message", type + " successful",
                "amount", amount,
                "newBalance", newBalance
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Transaction failed: " + ex.getMessage()));
        }
    }


    


}
