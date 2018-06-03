package com.example.vitalina.moneytrack.ui.auth;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.ui.AuthView;
import com.example.vitalina.moneytrack.ui.MainActivity;
import com.example.vitalina.moneytrack.ui.MenuActivity;
import com.example.vitalina.moneytrack.ui.auth.presenter.AuthPresenter;

public class SignInFragment extends Fragment implements AuthView{
    private Button vConfirm;
    private EditText vEmail, vPassword;
    private AuthPresenter presenter;
    private TextView vSignUp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.aurhorization,container,false);
        vConfirm = v.findViewById(R.id.authorization_confirm);
        vEmail = v.findViewById(R.id.authorization_email);
        vSignUp = v.findViewById(R.id.authorization_sign_up);
        vPassword = v.findViewById(R.id.authorization_password);
        presenter = new AuthPresenter(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vConfirm.setOnClickListener(view1 -> {
            if (!vEmail.getText().toString().isEmpty() && !vPassword.getText().toString().isEmpty()) {
                presenter.login(vEmail.getText().toString(), vPassword.getText().toString());
            } else {
                onLoginFailed("Credentials not filled");
            }
        });
        vSignUp.setOnClickListener(view12 -> ((MainActivity) getActivity()).goToSignpUp());
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(getActivity(), MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onLoginFailed(String message) {
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
    }
}
