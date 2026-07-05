package com.example.myapplication;

public class Claim {
    private String itemName;
    private String date;
    private String status;

    public Claim(String itemName, String date, String status) {
        this.itemName = itemName;
        this.date = date;
        this.status = status;
    }

    public String getItemName() { return itemName; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
}