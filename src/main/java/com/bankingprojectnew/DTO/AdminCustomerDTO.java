package com.bankingprojectnew.DTO;

import java.util.Date; // or java.time.LocalDate

public class AdminCustomerDTO {

	public AdminCustomerDTO() {}

	private long customerId;
	private String customerName;
	private String customerEmail;
	private long customerPhone;
	private Date customerDateOfBirth;  // <-- Fix this type

	public AdminCustomerDTO(long customerId, String customerName, String customerEmail, long customerPhone, Date customerDateOfBirth) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
		this.customerDateOfBirth = customerDateOfBirth;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
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

	public Date getCustomerDateOfBirth() {
		return customerDateOfBirth;
	}

	public void setCustomerDateOfBirth(Date customerDateOfBirth) {
		this.customerDateOfBirth = customerDateOfBirth;
	}

	// Getters & Setters ...
	
}
