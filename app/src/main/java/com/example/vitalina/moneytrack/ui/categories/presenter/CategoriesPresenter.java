package com.example.vitalina.moneytrack.ui.categories.presenter;

import android.util.Log;

import com.example.vitalina.moneytrack.data.AnalysFirestore;
import com.example.vitalina.moneytrack.data.CategoriesFirestore;
import com.example.vitalina.moneytrack.model.AnalysManager;
import com.example.vitalina.moneytrack.model.MostSpentListener;
import com.example.vitalina.moneytrack.model.entities.Analys;
import com.example.vitalina.moneytrack.model.entities.Categorie;
import com.example.vitalina.moneytrack.model.entities.Transaction;

import java.util.Map;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CategoriesPresenter {
    private CategoriesFirestore firestore;
    private CompositeDisposable disposable = new CompositeDisposable();
    private CategoriesView mView;
    public CategoriesPresenter(CategoriesView view) {
        firestore = new CategoriesFirestore();
        mView = view;

    }

    public void getCategories() {
        disposable.add(firestore.getCategories()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> {
                    AnalysManager.getInstance().startAnalys(next);
                    mView.displayCategories(next);
                }, error -> {
                    mView.errorLoadingCategories(error.getMessage());
                }));
    }

}
