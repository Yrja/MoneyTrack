package com.example.vitalina.moneytrack.ui.analys.presenter;

import android.util.Log;

import com.example.vitalina.moneytrack.data.AnalysFirestore;
import com.example.vitalina.moneytrack.model.entities.Analys;
import com.example.vitalina.moneytrack.model.entities.Categorie;
import com.example.vitalina.moneytrack.model.entities.Transaction;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AnalysPresenter {
    private AnalysFirestore firestore;
    private Disposable analysDisposable;
    private AnalysView mView;
    public AnalysPresenter(AnalysView mView) {
        firestore = new AnalysFirestore();
        this.mView = mView;
    }

    public void startAnalys(Categorie categorie) {

        if (analysDisposable!=null && !analysDisposable.isDisposed()){
            analysDisposable.dispose();
        }
        Multimap<Transaction,Transaction> proposals = ArrayListMultimap.create();
        analysDisposable = firestore.findProposesForCategory(categorie)
                .subscribeOn(Schedulers.newThread())
                .collect(()->proposals, (i,p)-> i.putAll(p))
                .doAfterSuccess(transactionTransactionHashMap -> {
                    if (mView!=null){
                        mView.displayAnalys(transactionTransactionHashMap);
                    }
                })
                .subscribe();
    }
}
