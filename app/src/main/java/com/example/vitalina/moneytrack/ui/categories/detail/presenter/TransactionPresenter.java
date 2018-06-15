package com.example.vitalina.moneytrack.ui.categories.detail.presenter;

import android.content.Context;
import android.location.Location;

import com.example.vitalina.moneytrack.data.CategoriesFirestore;
import com.example.vitalina.moneytrack.model.LocationManager;
import com.example.vitalina.moneytrack.model.entities.Categorie;
import com.example.vitalina.moneytrack.model.entities.Transaction;

import java.util.ArrayList;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TransactionPresenter {
    private CategoriesFirestore firestore;
    private CompositeDisposable disposable = new CompositeDisposable();
    private TransactionView mView;
    private LocationManager mLocationManager;
    private Location lastLocation;

    public TransactionPresenter(TransactionView mView, Context context) {
        this.mView = mView;
        firestore = new CategoriesFirestore();
        mLocationManager = new LocationManager(context);
        mLocationManager
                .getLocationSubject()
                .subscribe(next -> {
                    lastLocation = next;
                }, error -> {
                });
    }

    public void makeTransaction(Categorie categorie, Transaction transaction, boolean needLocation) {
        if (needLocation) {
            transaction.setLatitude(lastLocation.getLatitude());
            transaction.setLongitude(lastLocation.getLongitude());
        }
        disposable.add(firestore.makeTransaction(categorie, transaction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    mView.onTransactionSuccess(transaction);
                }, error -> {
                    mView.onTransactionFailed(error.getMessage());
                }));

    }

    public void loadTransaction(Categorie categorie, Date date) {
        disposable.add(firestore.getTransactions(categorie, date)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> {
                    mView.onTransactionsLoaded(next);
                }, error -> {
                    mView.onTransactionsLoaded(new ArrayList<>());
                }));

    }
    public void deleteCategory(Categorie categorie){
        disposable.add(firestore.deleteCategorie(categorie)
        .subscribe(()->{mView.onDeleteSuccess();}));
    }
}
