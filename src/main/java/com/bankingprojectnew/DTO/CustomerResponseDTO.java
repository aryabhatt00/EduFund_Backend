package com.bankingprojectnew.DTO; // Optional package

public class CustomerResponseDTO {
    private Long customerId;
    private Long accountNumber;

    public CustomerResponseDTO(int i, Long accountNumber) {
        this.customerId = (long) i;
        this.accountNumber = accountNumber;
    }

    public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public long getCustomerId() {
        return customerId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }
}
