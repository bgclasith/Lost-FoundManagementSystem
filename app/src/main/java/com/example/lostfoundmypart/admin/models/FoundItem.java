package com.example.lostfoundmypart.admin.models;

public class FoundItem {

    private String itemName;
    private String description;
    private String location;
    private String dateFound;
    private String itemImage;
    private String itemId;
    private String userId;
    private String status;

    public FoundItem() {
    }

    public FoundItem(String itemName,
                     String description,
                     String location,
                     String dateFound,
                     String itemImage,
                     String itemId,
                     String userId,
                     String status) {

        this.itemName = itemName;
        this.description = description;
        this.location = location;
        this.dateFound = dateFound;
        this.itemImage = itemImage;
        this.itemId = itemId;
        this.userId = userId;
        this.status = status;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }
    public String getDateFound() {
        return dateFound;
    }
    public String getItemImage() {
        return itemImage;
    }
    public String getItemId() {
        return itemId;
    }
    public String getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }
}