package com.example.vitalina.moneytrack.ui.auth.presenter;

import com.example.vitalina.moneytrack.data.CredentialsPreference;
import com.example.vitalina.moneytrack.model.entities.Credentials;
import com.example.vitalina.moneytrack.ui.AuthView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthPresenter {
    private FirebaseAuth auth;
    private AuthView mView;
    private CredentialsPreference preference;
    public AuthPresenter(AuthView mView, CredentialsPreference preference) {
        auth = FirebaseAuth.getInstance();
        this.mView = mView;
        this.preference = preference;
    }

    public void relogout(){
        Credentials credentials = preference.getCredentials();
        if (credentials!=null){
            mView.onLoginStarted();
            login(credentials.getEmail(),credentials.getPassword());
        } else {
            mView.onLoginFailed(null);
        }
    }

    public void login(String email,String password){
        mView.onLoginStarted();
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        preference.setCredentials(new Credentials(email,password));
                        FirebaseUser user = auth.getCurrentUser();
                        mView.onLoginSuccess();
                        // todo add get from db logic
                    } else {
                        preference.setCredentials(new Credentials(null,null));
                        mView.onLoginFailed(task.getException().getMessage());
                    }
                });
    }
}
