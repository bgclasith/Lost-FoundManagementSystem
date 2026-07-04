package com.example.lostfoundmypart.admin.models;

public class LostItem {

    private String itemName;
    private String description;
    private String location;
    private String dateLost;
    private String itemImage;
    private String userId;
    private String itemId;
    private String status;
    private String category;

    public LostItem() {
    }

    public LostItem(String itemName,
                    String description,
                    String location,
                    String dateLost,
                    String itemImage,
                    String itemId,
                    String userId,
                    String status,
                    String category) {

        this.itemName = itemName;
        this.description = description;
        this.location = location;
        this.dateLost = dateLost;
        this.itemImage = itemImage;
        this.itemId = itemId;
        this.userId = userId;
        this.status = status;
        this.category = category;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {return location;}
    public String getDateLost() {
        return dateLost;
    }
    public String getItemImage() {return itemImage;}
    public String getItemId() {
        return itemId;
    }
    public String getUserId() {
        return userId;
    }

    public String getStatus(){

        return status;
    }
    public String getCategory(){
        return category;
    }
}