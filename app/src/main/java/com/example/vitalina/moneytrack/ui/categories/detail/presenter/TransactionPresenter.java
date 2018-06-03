package com.example.vitalina.moneytrack.ui.categories.detail.presenter;

import com.example.vitalina.moneytrack.data.CategoriesFirestore;
import com.example.vitalina.moneytrack.model.entities.Categorie;
import com.example.vitalina.moneytrack.model.entities.Transaction;

import java.util.ArrayList;
import java.util.Date;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TransactionPresenter {
    private CategoriesFirestore firestore;
    private CompositeDisposable disposable = new CompositeDisposable();
    private TransactionView mView;

    public TransactionPresenter(TransactionView mView) {
        this.mView = mView;
        firestore = new CategoriesFirestore();
    }

    public void makeTransaction(Categorie categorie, Transaction transaction){
        disposable.add(firestore.makeTransaction(categorie,transaction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    mView.onTransactionSuccess();
                },error->{
                    mView.onTransactionFailed(error.getMessage());
                }));
    }
    public void loadTransaction(Categorie categorie, Date date){
        disposable.add(firestore.getTransactions(categorie,date)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(next->{
            mView.onTransactionsLoaded(next);
        },error->{
            mView.onTransactionsLoaded(new ArrayList<>());
        }));

    }
}
