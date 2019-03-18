package com.example.loginandregister.AccountActivity;


public class User {

    public String userID, first_name, password, last_name, email_address, phone_number, address_l1, state, postal_code;

    public User() {
    }

    public User(String userID, String first_name, String last_name, String password, String email_address, String phone_number, String address_l1, String postal_code, String state) {
        this.userID = userID;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.email_address = email_address;
        this.phone_number = phone_number;
        this.address_l1 = address_l1;
        this.postal_code = postal_code;
        this.state = state;
    }

    public String getUserID() {
        return userID;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getPassword() {
        return password;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail_address() {
        return email_address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getAddress_l1() {
        return address_l1;
    }


    public String getState() {
        return state;
    }

    public String getPostal_code() {
        return postal_code;
    }
}
