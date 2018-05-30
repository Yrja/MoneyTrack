package com.example.vitalina.moneytrack.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.ui.auth.SignInFragment;
import com.example.vitalina.moneytrack.ui.auth.SignUpFragment;

public class MainActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goToAuth();

    }
    public void goToAuth(){
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new SignInFragment())
                .commit();
    }
    public void goToSignpUp(){
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new SignUpFragment())
                .commit();
    }
}
