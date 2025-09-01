package com.bankingprojectnew.Entity;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class BankTransaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;
    
    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account transactionAccount;
    
    private double transactionAmount;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    @Enumerated(EnumType.STRING)
    private BankTransactionType transactionType;

    public BankTransaction() {
        this.transactionDate = new Date();
    }

    public BankTransaction(BankTransactionType transactionType, int transactionAmount, Account transactionAccount) {
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionAccount = transactionAccount;
        this.transactionDate = new Date();
    }
    private double balanceAfterTransaction;

    public double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(double balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }


    // Getters and Setters
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Account getTransactionAccount() {
        return transactionAccount;
    }

    public void setTransactionAccount(Account transactionAccount) {
        this.transactionAccount = transactionAccount;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double amount) {
        this.transactionAmount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BankTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(BankTransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
