package com.bankingprojectnew.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;
    
    @Column(unique = true)
    private long accountNumber;

    @Min(value = 0, message = "Balance must be non-negative")
    private double accountBalance;

    @JsonIgnore

    @OneToOne(mappedBy = "customerAccount")
    @JoinColumn(name = "account_id")
    private Customer customer;
    
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public long getAccountId() {
        return accountId;
    }
    public void setAccountId(int d) {
        this.accountId = d;
    }
    public long getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(long generatedAccountNumber) {
        this.accountNumber = generatedAccountNumber;
    }
    public @Min(value = 0, message = "Balance must be non-negative") double getAccountBalance() {
        return accountBalance;
    }
    public void setAccountBalance(double d) {
        this.accountBalance = d;
    }
}
