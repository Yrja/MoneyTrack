package com.example.vitalina.moneytrack.ui.profile.presenter;

import com.example.vitalina.moneytrack.model.entities.User;

public interface ProfileView {
    void displayProfile(User user);
    void userLoadError(String message);
}
