package com.example.vitalina.moneytrack.ui.categories.presenter;

import com.example.vitalina.moneytrack.model.entities.Categorie;

import java.util.List;

public interface CategoriesView {
    void displayCategories(List<Categorie> categories);
    void errorLoadingCategories(String message);

}
