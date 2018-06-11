package com.example.vitalina.moneytrack.data;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class Preference  {
    protected SharedPreferences mPreference;
    public Preference(Context context) {
        mPreference =  context.getSharedPreferences(getFilename(),Context.MODE_PRIVATE);
    }
    protected abstract String getFilename();
}
