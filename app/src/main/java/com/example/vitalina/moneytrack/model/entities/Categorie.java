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

    public Categorie(int id, String name, String icon, float freeMoney) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.freeMoney = freeMoney;
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

    public static HashMap<String, Categorie> getDefaultCategories(){
        HashMap<String, Categorie> categories = new HashMap<>();
        categories.put("Transport",new Categorie(0,"Transport",null,0));
        categories.put("Fun",new Categorie(1,"Fun", null, 0));
        categories.put("Food",new Categorie(2,"Food",null, 0));
        categories.put("Gifts",new Categorie(3,"Gifts",null, 0));
        categories.put("Health",new Categorie(4,"Health",null, 0));
        categories.put("House",new Categorie(5,"House",null, 0));
        categories.put("Credit",new Categorie(6,"Credit",null, 0));
        return categories;
    }
}
