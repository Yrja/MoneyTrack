package com.example.vitalina.moneytrack.model.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analys {
    private List<Categorie> analysCategorie;
    private HashMap<Categorie, SpentByCategorie> spendByCategorie;
    private MostSpentAnalys mostSpentAnalys;
    private Date dateOfAnalys;

    public Analys() {
        spendByCategorie = new HashMap<>();
        mostSpentAnalys = new MostSpentAnalys();
        analysCategorie = new ArrayList<>();
    }


    public Analys(List<Categorie> analysCategorie, HashMap<Categorie, SpentByCategorie> spendByCategorie, MostSpentAnalys mostSpentAnalys, Date dateOfAnalys) {
        this.analysCategorie = analysCategorie;
        this.spendByCategorie = spendByCategorie;
        this.mostSpentAnalys = mostSpentAnalys;
        this.dateOfAnalys = dateOfAnalys;
    }

    public HashMap<Categorie, SpentByCategorie> getSpendByCategorie() {
        return spendByCategorie;
    }

    public void setSpendByCategorie(HashMap<Categorie, SpentByCategorie> spendByCategorie) {
        this.spendByCategorie = spendByCategorie;
    }

    public void setTotalSpendByMonth(double totalSpendByMonth) {
        this.mostSpentAnalys.setSum(totalSpendByMonth);
    }

    public Date getDateOfAnalys() {
        return dateOfAnalys;
    }

    public void setDateOfAnalys(Date dateOfAnalys) {
        this.dateOfAnalys = dateOfAnalys;
    }

    public MostSpentAnalys getMostSpentAnalys() {
        return mostSpentAnalys;
    }

    public void setMostSpentAnalys(MostSpentAnalys mostSpentAnalys) {
        this.mostSpentAnalys = mostSpentAnalys;
    }

    public void putResult(Categorie categorie, SpentByCategorie spentByCategorie) {
        spendByCategorie.put(categorie, spentByCategorie);
    }

    public List<Categorie> getAnalysCategorie() {
        return analysCategorie;
    }

    public void setAnalysCategorie(List<Categorie> analysCategorie) {
        this.analysCategorie = analysCategorie;
    }
    public Map.Entry<Categorie,SpentByCategorie> getCategorieByName(String name){
        for (Map.Entry<Categorie,SpentByCategorie> entry :spendByCategorie.entrySet()){
            if (entry.getKey().getName().equals(name)){
                return entry;
            }
        }
        return null;
    }
    public class MostSpentAnalys {
        private Categorie categorie;
        private Double sum;
        private String mostSpentGoods;

        public MostSpentAnalys(Categorie categorie, Double sum, String mostSpentGoods) {
            this.categorie = categorie;
            this.sum = sum;
            this.mostSpentGoods = mostSpentGoods;
        }

        public MostSpentAnalys() {
        }

        public Categorie getCategorie() {
            return categorie;
        }

        public void setCategorie(Categorie categorie) {
            this.categorie = categorie;
        }

        public Double getSum() {
            return sum;
        }

        public void setSum(Double sum) {
            this.sum = sum;
        }

        public String getMostSpentGoods() {
            return mostSpentGoods;
        }

        public void setMostSpentGoods(String mostSpentGoods) {
            this.mostSpentGoods = mostSpentGoods;
        }
    }
    public void clear(){
        spendByCategorie = new HashMap<>();
        mostSpentAnalys = new MostSpentAnalys();
        analysCategorie = new ArrayList<>();
    }
}
