package com.example.vitalina.moneytrack.model.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("id")
    private String id;

    @SerializedName("avatar")
    private String avatar;

//    @SerializedName("categories")
//    private List<Categorie> categories;

    public User(String id, String firstName, String lastName, String avatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.avatar = avatar;
      //  this.categories = categories;
    }

    public User() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }



    @Override
    public String toString() {
        return
                "User{" +
                        "firstName = '" + firstName + '\'' +
                        ",lastName = '" + lastName + '\'' +
                        ",id = '" + id + '\'' +
                        ",avatar = '" + avatar + '\'' +
                        "}";
    }

    public static User buildNewUser(String id, String avatar) {
        User user = new User();
        user.setId(id);
        user.setAvatar(avatar);

//        List<Categorie> categories = new ArrayList<>();
//        categories.add(new Categorie(0,"Transport",null,0));
//        categories.add(new Categorie(1,"Fun", null, 0));
//        categories.add(new Categorie(2,"Food",null, 0));
//        categories.add(new Categorie(3,"Gifts",null, 0));
//        categories.add(new Categorie(4,"Health",null, 0));
//        categories.add(new Categorie(5,"House",null, 0));
//        categories.add(new Categorie(6,"Credit",null, 0));
//     //   user.setCategories(categories);

        return user;
    }
}