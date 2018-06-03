package com.example.vitalina.moneytrack.data;

import android.util.Log;

import com.example.vitalina.moneytrack.model.entities.Categorie;
import com.example.vitalina.moneytrack.model.entities.User;
import com.example.vitalina.moneytrack.model.exceptions.UserNotFoundException;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.Completable;
import io.reactivex.Single;

public class UserFirestore {
    FirebaseFirestore db;

    public UserFirestore() {
        db = FirebaseFirestore.getInstance();
    }

    public Single<User> createNewUser(String id, String avatar) {
        return Single.create(emitter -> {
            User user = User.buildNewUser(id, avatar);
            db.collection("users").document(id)
                    .set(user)
                    .addOnSuccessListener(documentReference -> {
                        for(Categorie categorie: Categorie.getDefaultCategories().values()){
                            db.collection("users")
                                    .document(id)
                                    .collection("categories")
                                    .document(categorie.getName())
                                    .set(categorie);
                        }
                        emitter.onSuccess(user);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Single<User> getUser(String id) {
        return Single.create(emitter -> {
            db.collection("users").document(id)
                    .get()
                    .addOnFailureListener(emitter::onError)
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            emitter.onSuccess(documentSnapshot.toObject(User.class));
                        } else {
                            emitter.onError(new UserNotFoundException("No such user in database"));
                        }
                    });
        });
    }
}
