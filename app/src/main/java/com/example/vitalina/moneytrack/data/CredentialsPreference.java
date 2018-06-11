package com.example.vitalina.moneytrack.data;

import android.content.Context;

import com.example.vitalina.moneytrack.model.entities.Credentials;

public class CredentialsPreference extends Preference {
    public CredentialsPreference(Context context) {
        super(context);
    }

    @Override
    protected String getFilename() {
        return "credentials";
    }

    public Credentials getCredentials() {
        String email = mPreference.getString("email", null);
        String password = mPreference.getString("password", null);
        if (email == null || password == null) {
            return null;
        } else
            return new Credentials(email, password);
    }
    public void setCredentials(Credentials credentials){
        mPreference.edit()
                .putString("email",credentials.getEmail())
                .putString("password",credentials.getPassword())
                .apply();
    }
}
