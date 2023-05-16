package com.anastas.carsshop.helper;

import javax.validation.constraints.*;

public class ChangeUserDataHelper {

    private String contactName;

    @Email(message = "User email have to be valid!")
    private String email;

    @Digits(integer = 10, fraction = 0, message = "Valid bulgarian number have to contain 10 digits!")
    private String phoneNumber;

    private String address;

    public ChangeUserDataHelper() {
    }

    public ChangeUserDataHelper(String contactName, String email, String phoneNumber, String address) {
        this.contactName = contactName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
