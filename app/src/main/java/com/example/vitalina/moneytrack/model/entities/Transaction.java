package com.example.vitalina.moneytrack.model.entities;

public class Transaction {
    private long time;
    private String description;
    private long sum;
    private double latitude;
    private double langitude;

    public Transaction(long time, String description, long sum, double latitude, double langitude) {
        this.time = time;
        this.description = description;
        this.sum = sum;
        this.latitude = latitude;
        this.langitude = langitude;
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

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return langitude;
    }

    public void setLangitude(double langitude) {
        this.langitude = langitude;
    }
}
