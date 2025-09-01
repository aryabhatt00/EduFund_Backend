package com.bankingprojectnew.DTO;

import java.util.Date;

public class AdminTransactionDTO {

    private long transactionId;
    private String transactionType;
    private double transactionAmount;
    private double balanceAfterTransaction;
    private Date transactionDate;
    private long accountNumber;
    private String customerName;

    public AdminTransactionDTO() {}

    public AdminTransactionDTO(long transactionId, String transactionType, double transactionAmount,
                                double balanceAfterTransaction, Date transactionDate,
                                long accountNumber, String customerName) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.transactionDate = transactionDate;
        this.accountNumber = accountNumber;
        this.customerName = customerName;
    }

    // GETTERS + SETTERS
    public long getTransactionId() { return transactionId; }
    public void setTransactionId(long transactionId) { this.transactionId = transactionId; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public double getTransactionAmount() { return transactionAmount; }
    public void setTransactionAmount(double transactionAmount) { this.transactionAmount = transactionAmount; }

    public double getBalanceAfterTransaction() { return balanceAfterTransaction; }
    public void setBalanceAfterTransaction(double balanceAfterTransaction) { this.balanceAfterTransaction = balanceAfterTransaction; }

    public Date getTransactionDate() { return transactionDate; }
    public void setTransactionDate(Date transactionDate) { this.transactionDate = transactionDate; }

    public long getAccountNumber() { return accountNumber; }
    public void setAccountNumber(long accountNumber) { this.accountNumber = accountNumber; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
}
