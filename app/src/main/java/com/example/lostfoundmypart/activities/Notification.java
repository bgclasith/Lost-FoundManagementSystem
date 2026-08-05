package com.example.myapplication;

public class Notification {
    public enum Type { APPROVED, MATCHING, RECOVERED, REJECTED }

    private String title;
    private String message;
    private String time;
    private String claimId;
    private Type type;
    private boolean isRead;

    public Notification(String title, String message, String time, String claimId, Type type, boolean isRead) {
        this.title = title;
        this.message = message;
        this.time = time;
        this.claimId = claimId;
        this.type = type;
        this.isRead = isRead;
    }

    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getTime() { return time; }
    public String getClaimId() { return claimId; }
    public Type getType() { return type; }
    public boolean isRead() { return isRead; }
}