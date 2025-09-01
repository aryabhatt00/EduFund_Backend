package com.bankingprojectnew.DTO;

public class TransactionRequest {
    private Long accountNumber;
    private Double amount;

    public TransactionRequest() {
        // Default constructor for JSON deserialization
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
