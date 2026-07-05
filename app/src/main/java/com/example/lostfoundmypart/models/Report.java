package com.example.lostfoundmypart.models;

public class Report {

    private String imageUri;
    private String itemName;
    private String category;
    private String description;
    private String date;
    private String location;
    private String reportType;
    private String status;

    public Report(String imageUri,
                  String itemName,
                  String category,
                  String description,
                  String date,
                  String location,
                  String reportType,
                  String status) {

        this.imageUri = imageUri;
        this.itemName = itemName;
        this.category = category;
        this.description = description;
        this.date = date;
        this.location = location;
        this.reportType = reportType;
        this.status = status;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getReportType() {
        return reportType;
    }

    public String getStatus() {
        return status;
    }
}