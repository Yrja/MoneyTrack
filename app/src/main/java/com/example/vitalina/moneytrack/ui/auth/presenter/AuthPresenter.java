package com.example.vitalina.moneytrack.ui.auth.presenter;

import com.example.vitalina.moneytrack.ui.AuthView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthPresenter {
    private FirebaseAuth auth;
    private AuthView mView;
    public AuthPresenter(AuthView mView) {
        auth = FirebaseAuth.getInstance();
        this.mView = mView;
    }

    public void login(String email,String password){
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        FirebaseUser user = auth.getCurrentUser();
                        mView.onLoginSuccess();
                        // todo add get from db logic
                    } else {
                        mView.onLoginFailed(task.getException().getMessage());
                    }
                });
    }
}
