package com.example.vitalina.moneytrack.model.entities;

public class Transaction {
    private long time;
    private String description;
    private double sum;
    private double latitude;
    private double longitude;

    public Transaction(long time, String description, long sum, double latitude, double langitude) {
        this.time = time;
        this.description = description;
        this.sum = sum;
        this.latitude = latitude;
        this.longitude = langitude;
    }

    public Transaction() {
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
