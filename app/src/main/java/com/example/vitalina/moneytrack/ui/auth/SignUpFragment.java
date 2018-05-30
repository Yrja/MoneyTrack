package com.example.vitalina.moneytrack.ui.auth;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.ui.MainActivity;
import com.example.vitalina.moneytrack.ui.MenuActivity;
import com.example.vitalina.moneytrack.ui.auth.presenter.SignUpPresenter;
import com.example.vitalina.moneytrack.ui.auth.presenter.SignUpView;

public class SignUpFragment extends Fragment implements SignUpView, TextWatcher {
    private EditText vEmail, vPassword, vConfirmPassword;
    private ImageView vAvatar;
    private Button vConfirm, vCancel;
    private SignUpPresenter signUpPresenter;
    private boolean isValid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_account, container, false);
        vEmail = v.findViewById(R.id.authorization_email);
        vPassword = v.findViewById(R.id.create_account_password);
        vConfirmPassword = v.findViewById(R.id.create_account_repeat_password);
        vAvatar = v.findViewById(R.id.profile_image);
        vCancel = v.findViewById(R.id.create_account_cancel);
        vConfirm = v.findViewById(R.id.create_account_confirm);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!vPassword.getText().toString().equals(vConfirmPassword.getText().toString())){
                    Toast.makeText(getActivity(),"Passwords does`nt mismatch", Toast.LENGTH_SHORT).show();
                } else {
                    signUpPresenter.signUp(vEmail.getText().toString(), vPassword.getText().toString());
                }
            }
        });
        vCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).goToAuth();
            }
        });

        signUpPresenter = new SignUpPresenter(this);

    }

    @Override
    public void onSignUp() {
        Intent intent = new Intent(getActivity(), MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onSignUpFailed(String message) {
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        isValid = vPassword.getText().toString().equals(vConfirmPassword.getText().toString())
                && !vPassword.getText().toString().isEmpty() && !vEmail.getText().toString().isEmpty();
    }
}
