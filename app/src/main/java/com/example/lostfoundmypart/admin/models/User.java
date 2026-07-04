package com.example.lostfoundmypart.admin.models;

public class User {

    private String fullName;
    private String email;
    private String phone;

    public User() {
    }

    public User(String fullName,
                String email,
                String phone) {

        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }

}