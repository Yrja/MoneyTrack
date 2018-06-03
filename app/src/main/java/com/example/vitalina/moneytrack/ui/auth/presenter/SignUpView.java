package com.example.vitalina.moneytrack.ui.auth.presenter;

import com.example.vitalina.moneytrack.model.entities.User;

public interface SignUpView {
    void onSignUp(User user);
    void onSignUpFailed(String message);
}
