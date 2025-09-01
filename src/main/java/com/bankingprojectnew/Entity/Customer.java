package com.bankingprojectnew.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Customer {
    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int customerId;

    @NotBlank(message = "Name is required")
    private String customerName;

    @Column(unique = true)
    @Email(message = "Enter a valid email")
    @NotBlank(message = "Email is required")
    private String customerEmail;
    
    private String password;

    @Column(unique = true)
    @Min(value = 1000000000L, message = "Phone number must be at least 10 digits")
    @Max(value = 9999999999L, message = "Phone number can't exceed 10 digits")
    private long customerPhone;
    
    
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date customerDateOfBirth;


	private String question1;
    
    private String question2;
    
    private String question3;
    
    @Embedded
    private Address customerAddress;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account customerAccount;
    
    
    
  
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		
		return password;
	}
	public Account getCustomerAccount() {
        return customerAccount;
    }
    public void setCustomerAccount(Account customerAccount) {
        this.customerAccount = customerAccount;
    }
    public Address getCustomerAddress() {
        return customerAddress;
    }
    public void setCustomerAddress(Address customerAddress) {
        this.customerAddress = customerAddress;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerEmail() {
        return customerEmail;
    }
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    public long getCustomerPhone() {
        return customerPhone;
    }
    public void setCustomerPhone(long customerPhone) {
        this.customerPhone = customerPhone;
    }
    public String getQuestion1() {
		return question1;
	}
	public void setQuestion1(String question1) {
		this.question1 = question1;
	}
	public String getQuestion2() {
		return question2;
	}
	public void setQuestion2(String question2) {
		this.question2 = question2;
	}
	public String getQuestion3() {
		return question3;
	}
	public void setQuestion3(String question3) {
		this.question3 = question3;
	
}
	public Date getCustomerDateOfBirth() {
		return customerDateOfBirth;
	}
	public void setCustomerDateOfBirth(Date customerDateOfBirth) {
		this.customerDateOfBirth = customerDateOfBirth;
	}

}
