package com.example.vitalina.moneytrack.model;

import com.example.vitalina.moneytrack.data.AnalysFirestore;
import com.example.vitalina.moneytrack.model.entities.Analys;
import com.example.vitalina.moneytrack.model.entities.Categorie;
import com.example.vitalina.moneytrack.model.entities.SpentByCategorie;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class AnalysManager {
    private AnalysFirestore firestore;
    private static AnalysManager instance;
    private Analys analysResult = new Analys();

    private AnalysManager() {
        firestore = new AnalysFirestore();
    }

    public static AnalysManager getInstance() {
        if (instance == null) {
            instance = new AnalysManager();
        }
        return instance;
    }

    public void startAnalys(List<Categorie> categories) {
        analysResult.clear();


        Disposable d = Observable.fromIterable(categories)
                .flatMapMaybe(obs -> firestore.calculateTransactions(obs))
                .toList()
                .subscribe(next -> {
                    analysResult.setAnalysCategorie(next);
                }, error -> {
                });

        Disposable d2 = Observable.fromIterable(categories)
                .subscribe(next -> {
                    firestore.calculateMostSpentGoods(next, result -> {
                        if (result != null) {
                            analysResult.putResult(next, new SpentByCategorie(result.getKey(), result.getValue()));
                        }
                    });
                });
    }

    public Analys getAnalysResult() {
        return analysResult;
    }
}
