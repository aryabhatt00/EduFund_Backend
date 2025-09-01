package com.bankingprojectnew.Entity;

import java.time.LocalDateTime;

//model/OtpRecord.java
public class OtpRecord {
 private String email;
 private String otp;
 private String transactionType; // "Deposit" or "Withdraw"
 private double amount;
 private LocalDateTime expiryTime;
 private boolean verified;
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getOtp() {
	return otp;
}
public void setOtp(String otp) {
	this.otp = otp;
}
public String getTransactionType() {
	return transactionType;
}
public void setTransactionType(String transactionType) {
	this.transactionType = transactionType;
}
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
public LocalDateTime getExpiryTime() {
	return expiryTime;
}
public void setExpiryTime(LocalDateTime expiryTime) {
	this.expiryTime = expiryTime;
}
public boolean isVerified() {
	return verified;
}
public void setVerified(boolean verified) {
	this.verified = verified;
}
public OtpRecord(String email, String otp, String transactionType, double amount, LocalDateTime expiryTime,
		boolean verified) {
	super();
	this.email = email;
	this.otp = otp;
	this.transactionType = transactionType;
	this.amount = amount;
	this.expiryTime = expiryTime;
	this.verified = verified;
}
public OtpRecord()
{
	}
 // constructors, getters, setters
}
