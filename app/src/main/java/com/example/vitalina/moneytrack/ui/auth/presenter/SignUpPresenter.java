package com.example.vitalina.moneytrack.ui.auth.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpPresenter {
    private FirebaseAuth mAuth;
    private SignUpView mView;

    public SignUpPresenter(SignUpView mView) {
        this.mView = mView;
        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            mView.onSignUp();
                        } else {
                            mView.onSignUpFailed(task.getException().getMessage());
                        }
                    }
                });
    }
}
