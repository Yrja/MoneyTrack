package com.example.vitalina.moneytrack.model.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Categorie implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("icon")
    private String icon;
    @SerializedName("freeMoney")
    private float freeMoney;
    @SerializedName("created_by_user")
    private boolean createdByUser;

    private double spendByMonth;
    private String mostSpendGoods;

    public Categorie(String name) {
        this.name = name;
    }

    public Categorie(int id, String name, String icon, float freeMoney) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.freeMoney = freeMoney;
    }
    public Categorie(int id, String name, String icon, float freeMoney, boolean createdByUser) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.freeMoney = freeMoney;
        this.createdByUser = createdByUser;
    }

    public Categorie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public float getFreeMoney() {
        return freeMoney;
    }

    public void setFreeMoney(float freeMoney) {
        this.freeMoney = freeMoney;
    }

    public boolean isCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(boolean createdByUser) {
        this.createdByUser = createdByUser;
    }

    public double getSpendByMonth() {
        return spendByMonth;
    }

    public void setSpendByMonth(double spendByMonth) {
        this.spendByMonth = spendByMonth;
    }

    public String getMostSpendGoods() {
        return mostSpendGoods;
    }

    public void setMostSpendGoods(String mostSpendGoods) {
        this.mostSpendGoods = mostSpendGoods;
    }

    public static HashMap<String, Categorie> getDefaultCategories(){
        HashMap<String, Categorie> categories = new HashMap<>();
        categories.put("Transport",new Categorie(0,"Transport","https://firebasestorage.googleapis.com/v0/b/moneytracker-5fb4e.appspot.com/o/transport.png?alt=media&token=ecacf37a-a553-4930-a685-855e48b0f6ec",0));
        categories.put("Fun",new Categorie(1,"Fun", "https://firebasestorage.googleapis.com/v0/b/moneytracker-5fb4e.appspot.com/o/fun.png?alt=media&token=4730114e-1594-46d6-8e62-af104fd2ede2", 0));
        categories.put("Food",new Categorie(2,"Food","https://firebasestorage.googleapis.com/v0/b/moneytracker-5fb4e.appspot.com/o/food.png?alt=media&token=d025568a-226c-4d47-8f2d-99c88d5a3d58", 0));
        categories.put("Gifts",new Categorie(3,"Gifts","https://firebasestorage.googleapis.com/v0/b/moneytracker-5fb4e.appspot.com/o/gifts.png?alt=media&token=0021c382-62b7-4c65-a39c-7d46137857c7", 0));
        categories.put("Health",new Categorie(4,"Health","https://firebasestorage.googleapis.com/v0/b/moneytracker-5fb4e.appspot.com/o/health.png?alt=media&token=a599df66-590c-4af0-91ef-892d6af27050", 0));
        categories.put("House",new Categorie(5,"House","https://firebasestorage.googleapis.com/v0/b/moneytracker-5fb4e.appspot.com/o/house.png?alt=media&token=e7226f23-ca51-4ced-9070-5a6a726707dc", 0));
        categories.put("Credit",new Categorie(6,"Credit","https://firebasestorage.googleapis.com/v0/b/moneytracker-5fb4e.appspot.com/o/credit.png?alt=media&token=3aca30dd-445f-4927-83ad-51fce753270d", 0));
        return categories;
    }
}
