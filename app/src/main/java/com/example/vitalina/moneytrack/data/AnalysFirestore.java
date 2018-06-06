package com.example.vitalina.moneytrack.data;

import android.util.Log;

import com.example.vitalina.moneytrack.model.MostSpentListener;
import com.example.vitalina.moneytrack.model.entities.Categorie;
import com.example.vitalina.moneytrack.model.entities.Transaction;
import com.example.vitalina.moneytrack.model.entities.User;
import com.example.vitalina.moneytrack.ui.utils.DateDayUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AnalysFirestore {
    private FirebaseFirestore db;

    public AnalysFirestore() {
        db = FirebaseFirestore.getInstance();
    }

    private Single<List<Categorie>> getCategoires() {
        return Single.create(emitter -> {
            db.collection("users")
                    .document(FirebaseAuth.getInstance().getUid())
                    .collection("categories")
                    .get().addOnFailureListener(emitter::onError)
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        emitter.onSuccess(queryDocumentSnapshots.toObjects(Categorie.class));
                    });
        });
    }

    private Observable<List<User>> getUsers() {
        return Observable.create(emitter -> {
            db.collection("users")
                    .get().addOnFailureListener(emitter::onError)
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        emitter.onNext(queryDocumentSnapshots.toObjects(User.class));
                        emitter.onComplete();
                    });
        });
    }

    private Single<Wrapper> getCategoire(String userId, String name) {
        return Single.create(emitter -> {
            db.collection("users")
                    .document(userId)
                    .collection("categories")
                    .document(name)
                    .get().addOnFailureListener(emitter::onError)
                    .addOnSuccessListener(documentSnapshots -> {
                        Wrapper wrapper = new Wrapper();
                        wrapper.setCategorie(documentSnapshots.toObject(Categorie.class));
                        wrapper.setUserId(userId);
                        emitter.onSuccess(wrapper);
                    });
        });

    }

    public Single<List<Transaction>> getTransactions(String name, long day, String userId) {
        return Single.create(emitter -> {
            db.collection("users")
                    .document(userId)
                    .collection("categories")
                    .document(name)
                    .collection(String.valueOf(day))
                    .get().addOnFailureListener(emitter::onError)
                    .addOnSuccessListener(elem -> {
                        if (!elem.isEmpty()) {
                            emitter.onSuccess(elem.toObjects(Transaction.class));
                        } else {
                            emitter.onSuccess(new ArrayList<>());
                        }
                    });
        });
    }

    public Observable<Wrapper> getTransactions(Wrapper wrapper) {
        return
                Observable.fromIterable(DateDayUtils.getDaysInterval())
                        .flatMap(day -> Observable.create(emitter -> {
                            db.collection("users")
                                    .document(wrapper.userId)
                                    .collection("categories")
                                    .document(wrapper.categorie.getName())
                                    .collection(String.valueOf(day))
                                    .get().addOnFailureListener(emitter::onError)
                                    .addOnSuccessListener(elem -> {
                                        if (!elem.isEmpty()) {
                                            Wrapper wrp = new Wrapper(wrapper.getUserId(), wrapper.getCategorie(), elem.toObjects(Transaction.class));
                                            emitter.onNext(wrp);
                                        } else {
                                            emitter.onNext(wrapper);
                                        }
                                        emitter.onComplete();
                                    });
                        }));
    }


    public Maybe<Categorie> calculateTransactions(Categorie categorie) {
        return Observable.fromIterable(DateDayUtils.getDaysInterval())
                .flatMapSingle(day -> getTransactions(categorie.getName(), day, FirebaseAuth.getInstance().getUid()))
                .flatMapIterable(transactions -> transactions)
                .map(Transaction::getSum)
                .filter(sum -> sum < 0)
                .reduce((aDouble, aDouble2) -> aDouble += aDouble2)
                .defaultIfEmpty(0.0)
                .map(dbl -> {
                    categorie.setSpendByMonth(dbl);
                    return categorie;
                });
    }

    public void calculateMostSpentGoods(Categorie categorie, MostSpentListener listener) {
        HashMap<String, Double> map = new HashMap<>();
        Disposable d = Observable.fromIterable(DateDayUtils.getDaysInterval())
                .subscribeOn(Schedulers.newThread())
                .flatMapSingle(day -> getTransactions(categorie.getName(), day, FirebaseAuth.getInstance().getUid()))
                .flatMapIterable(transactions -> transactions)
                .filter(note -> note.getDescription() != null)
                .filter(note -> !note.getDescription().isEmpty())
                .filter(note -> note.getSum() < 0)
                .doOnNext(note -> {
                    double dbl = map.get(note.getDescription()) != null ? map.get(note.getDescription()) : 0;
                    map.put(note.getDescription(), dbl + note.getSum());
                })
                .subscribe(next -> {
                }, error -> {
                }, () -> {
                    Map.Entry<String, Double> maxEntry = null;
                    for (Map.Entry<String, Double> entry : map.entrySet()) {
                        if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) < 0) {
                            maxEntry = entry;
                        }
                    }
                    listener.receiveMostForCategorie(maxEntry);
                });
    }


    public Observable<Multimap<Transaction, Transaction>> findProposesForCategory(Categorie categorie) {
        return Observable.create(emitter -> {
            Observable<List<Transaction>> userTransactions = Observable.fromIterable(DateDayUtils.getDaysInterval())
                    .subscribeOn(Schedulers.newThread())
                    .flatMapSingle(day -> getTransactions(categorie.getName(), day, FirebaseAuth.getInstance().getUid()))
                    .filter(it -> it.size() > 0)
                    .reduce((l1, l2) -> {
                        l1.addAll(l2);
                        return l1;
                    })
                    .toObservable();


            Observable<List<Transaction>> analysTransactions = getUsers()
                    .flatMapIterable(users -> users)
                    .filter(user -> !user.getId().equals(FirebaseAuth.getInstance().getUid()))
                    .flatMapSingle(user -> getCategoire(user.getId(), categorie.getName()))
                    .flatMap(this::getTransactions)
                    .map(Wrapper::getTransactions)
                    .filter(it -> it.size() > 0);


            Disposable dis = Observable.combineLatest(userTransactions, analysTransactions,
                    (my, propose) -> {
                        Multimap<Transaction, Transaction> result = ArrayListMultimap.create();
                        for (Transaction myTransaction : my) {
                            for (Transaction proposeTransaction : propose) {
                                if (myTransaction.getDescription().equals(proposeTransaction.getDescription())
                                        && myTransaction.getSum() < proposeTransaction.getSum() && proposeTransaction.getSum() < 0) {
                                    result.put(myTransaction, proposeTransaction);
                                }
                            }
                        }
                        return result;
                    }).subscribe(
                    emitter::onNext, error->{}, emitter::onComplete
            );

        });
    }

    private HashMap<Transaction, Transaction> combine(List<Transaction> propose, List<Transaction> my) {
        HashMap<Transaction, Transaction> transactions = new HashMap<>();
        for (Transaction myTransaction : my) {
            for (Transaction proposeTransaction : propose) {
                if (myTransaction.getDescription().equals(proposeTransaction.getDescription())
                        && myTransaction.getSum() < proposeTransaction.getSum() && proposeTransaction.getSum() < 0) {
                    transactions.put(myTransaction, proposeTransaction);
                }
            }
        }
        return transactions;
    }

    public class Wrapper {
        String userId;
        Categorie categorie;
        List<Transaction> transactions;

        public Wrapper() {
            transactions = new ArrayList<>();
        }

        public Wrapper(String userId, Categorie categorie, List<Transaction> transactions) {
            this.userId = userId;
            this.categorie = categorie;
            this.transactions = transactions;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Categorie getCategorie() {
            return categorie;
        }

        public void setCategorie(Categorie categorie) {
            this.categorie = categorie;
        }

        public List<Transaction> getTransactions() {
            return transactions;
        }

        public void setTransactions(List<Transaction> transactions) {
            this.transactions = transactions;
        }
    }
}
