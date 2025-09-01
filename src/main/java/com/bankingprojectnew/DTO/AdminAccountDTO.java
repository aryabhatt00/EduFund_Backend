package com.bankingprojectnew.DTO;

public class AdminAccountDTO {

    private long accountId;
    private long accountNumber;
    private double accountBalance;
    private String customerName;

    // Default constructor (required for JPA projection)
    public AdminAccountDTO() {}

    // Constructor for custom queries
    public AdminAccountDTO(long accountId, long accountNumber, double accountBalance, String customerName) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.customerName = customerName;
    }

    // Getters & Setters
    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
