package com.bankingprojectnew.DTO;

public class CustomerLoginResponseDTO {
    private String token;
    private String name;
    private String email;

    // ✅ Default constructor (required for object creation via setters)
    public CustomerLoginResponseDTO() {
    }

    // ✅ Parameterized constructor
    public CustomerLoginResponseDTO(String token, String name, String email) {
        this.token = token;
        this.name = name;
        this.email = email;
    }

    // ✅ Getters and setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
