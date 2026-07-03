package com.example.lostfoundmypart.admin.models;

public class Claim {

    private String itemId;
    private String itemName;
    private String claimantName;
    private String claimantEmail;
    private String claimantId;
    private String claimId;
    private String proof;
    private String status;
    private String itemImage;

    public Claim() {
    }

    public Claim(String itemId,
                 String itemName,
                 String claimantName,
                 String claimantEmail,
                 String claimantId,
                 String claimId,
                 String proof,
                 String status,
                 String itemImage) {

        this.itemId = itemId;
        this.itemName = itemName;
        this.claimantName = claimantName;
        this.claimantEmail = claimantEmail;
        this.claimantId = claimantId;
        this.claimId = claimId;
        this.proof = proof;
        this.status = status;
        this.itemImage = itemImage;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getClaimantName() {
        return claimantName;
    }

    public String getClaimantEmail() {
        return claimantEmail;
    }

    public String getClaimantId() {
        return claimantId;
    }

    public String getClaimId() {
        return claimId;
    }

    public String getProof() {
        return proof;
    }

    public String getStatus() {
        return status;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }
}