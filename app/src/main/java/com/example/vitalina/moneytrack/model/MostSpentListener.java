package com.example.vitalina.moneytrack.model;

import java.util.Map;

public interface MostSpentListener {
    void receiveMostForCategorie(Map.Entry<String,Double> result);
}
