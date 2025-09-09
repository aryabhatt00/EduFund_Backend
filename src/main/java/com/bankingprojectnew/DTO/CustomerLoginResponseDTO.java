package com.bankingprojectnew.DTO;

public class CustomerLoginResponseDTO {
    private String token;
    private String name;
    private String email;
    private Long accountNumber; 

    public CustomerLoginResponseDTO() {}

    public CustomerLoginResponseDTO(String token, String name, String email, Long accountNumber) {
        this.token = token;
        this.name = name;
        this.email = email;
        this.accountNumber = accountNumber;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getAccountNumber() { return accountNumber; }
    public void setAccountNumber(Long accountNumber) { this.accountNumber = accountNumber; }
}
