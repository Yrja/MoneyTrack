package com.example.vitalina.moneytrack.ui;

public interface AuthView {
    void onLoginSuccess();
    void onLoginFailed(String message);
    void onLoginStarted();
}
