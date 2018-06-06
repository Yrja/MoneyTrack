package com.example.vitalina.moneytrack.model.entities;

public class SpentByCategorie {
    private String categorie;
    private Double sum;

    public SpentByCategorie(String categorie, Double sum) {
        this.categorie = categorie;
        this.sum = sum;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
