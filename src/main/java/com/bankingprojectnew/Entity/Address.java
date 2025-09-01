package com.bankingprojectnew.Entity;


import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    
    private String streetName;
    private String countryName;
    private int pinCode;
    
    public String getStreetName() {
        return streetName;
    }
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public int getPinCode() {
        return pinCode;
    }
    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }
}

