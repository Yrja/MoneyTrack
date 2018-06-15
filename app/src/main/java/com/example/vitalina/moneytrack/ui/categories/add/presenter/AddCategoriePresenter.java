package com.example.vitalina.moneytrack.ui.categories.add.presenter;

import android.support.v7.view.menu.BaseMenuPresenter;

import com.example.vitalina.moneytrack.data.CategoriesFirestore;
import com.example.vitalina.moneytrack.model.entities.Categorie;

import io.reactivex.disposables.Disposable;

public class AddCategoriePresenter {
    private CategoriesFirestore firestore;
    private AddCategorieView mView;

    public AddCategoriePresenter(AddCategorieView view) {
        firestore = new CategoriesFirestore();
        this.mView = view;
    }

    public void addCategorie(Categorie categorie) {
        Disposable d = firestore.addCategorie(categorie)
                .subscribe(() -> {
                            mView.onAddSuccess();
                        },
                        error -> {
                            mView.onAddSuccess();
                        });
    }
}
