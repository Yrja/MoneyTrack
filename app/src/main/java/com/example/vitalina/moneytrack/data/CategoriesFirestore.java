package com.example.vitalina.moneytrack.data;

import com.example.vitalina.moneytrack.model.entities.Categorie;
import com.example.vitalina.moneytrack.model.entities.Transaction;
import com.example.vitalina.moneytrack.ui.utils.DateUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class CategoriesFirestore {
    private FirebaseFirestore db;

    public CategoriesFirestore() {
        db = FirebaseFirestore.getInstance();
    }

    public Single<List<Categorie>> getCategories() {
        return Single.create(emitter -> {
            List<Categorie> categories = new ArrayList<>();
            db.collection("users")
                    .document(FirebaseAuth.getInstance().getUid())
                    .collection("categories")
                    .get().addOnFailureListener(emitter::onError)
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            categories.add(document.toObject(Categorie.class));
                        }
                        emitter.onSuccess(categories);
                    });
        });
    }

    public Single<List<Transaction>> getTransactions(Categorie categorie, Date date) {
        return Single.create(emitter -> {
            List<Transaction> transactions = new ArrayList<>();
            db.collection("users")
                    .document(FirebaseAuth.getInstance().getUid())
                    .collection("categories")
                    .document(categorie.getName())
                    .collection(String.valueOf(DateUtils.getDay(date)))
                    .get().addOnFailureListener(emitter::onError)
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            transactions.add(document.toObject(Transaction.class));
                        }
                        emitter.onSuccess(transactions);
                    });
        });
    }

    public Completable makeTransaction(Categorie categorie, Transaction transaction) {
        return Completable.create(emitter -> {
            db.collection("users")
                    .document(FirebaseAuth.getInstance().getUid())
                    .collection("categories")
                    .document(categorie.getName())
                    .collection(String.valueOf(DateUtils.getDay(new Date(transaction.getTime()))))
                    .document(String.valueOf(transaction.getTime()))
                    .set(transaction)
                    .addOnFailureListener(emitter::onError)
                    .addOnSuccessListener(documentReference -> {
                        emitter.onComplete();
                    });
        }).andThen(updateFreeMoney(categorie, transaction));
    }

    public Completable updateFreeMoney(Categorie categorie, Transaction transaction) {
        //TODO BUG HERE
        return Completable.create(emitter -> {
            db.collection("users")
                    .document(FirebaseAuth.getInstance().getUid())
                    .collection("categories")
                    .document(categorie.getName())
                    .update("freeMoney", categorie.getFreeMoney() + transaction.getSum())
                    .addOnFailureListener(emitter::onError)
                    .addOnSuccessListener(snapshot -> {
                        emitter.onComplete();
                    });
        });
    }

    public Completable addCategorie(Categorie categorie) {
       return Completable.create(emitter -> {
            db.collection("users")
                    .document(FirebaseAuth.getInstance().getUid())
                    .collection("categories")
                    .document(categorie.getName())
                    .set(categorie).addOnFailureListener(emitter::onError)
                    .addOnSuccessListener(aVoid -> {
                        emitter.onComplete();
                    });
        });
    }

    public Completable deleteCategorie(Categorie categorie){
        return Completable.create(emitter->{
            db.collection("users")
                    .document(FirebaseAuth.getInstance().getUid())
                    .collection("categories")
                    .document(categorie.getName())
                    .delete().addOnFailureListener(emitter::onError)
                    .addOnSuccessListener(aVoid -> {
                        emitter.onComplete();
                    });
        });
    }
}
