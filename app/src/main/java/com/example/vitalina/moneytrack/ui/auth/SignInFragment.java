package com.example.vitalina.moneytrack.ui.auth;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.data.CredentialsPreference;
import com.example.vitalina.moneytrack.ui.AuthView;
import com.example.vitalina.moneytrack.ui.MainActivity;
import com.example.vitalina.moneytrack.ui.MenuActivity;
import com.example.vitalina.moneytrack.ui.auth.presenter.AuthPresenter;
import com.narayanacharya.waveview.WaveView;

public class SignInFragment extends Fragment implements AuthView {
    private Button vConfirm;
    private EditText vEmail, vPassword;
    private AuthPresenter presenter;
    private TextView vSignUp;
    private ConstraintLayout vLoginRunning, vLoginContainer;
    private ImageView vBottomImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.aurhorization, container, false);
        vConfirm = v.findViewById(R.id.authorization_confirm);
        vEmail = v.findViewById(R.id.authorization_email);
        vSignUp = v.findViewById(R.id.authorization_sign_up);
        vPassword = v.findViewById(R.id.authorization_password);
        vLoginRunning = v.findViewById(R.id.vLoginRunning);
        vLoginContainer = v.findViewById(R.id.vLoginContainer);
        vBottomImage = v.findViewById(R.id.imageView);


        presenter = new AuthPresenter(this, new CredentialsPreference(getActivity()));
        presenter.relogout();
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
        getActivity().finish();
    }

    @Override
    public void onLoginFailed(String message) {
        if (message != null)
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        vLoginContainer.setVisibility(View.VISIBLE);
        vLoginRunning.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoginStarted() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) vBottomImage.getLayoutParams();
        params.width = width * 2;
        vBottomImage.setLayoutParams(params);
        vLoginContainer.setVisibility(View.INVISIBLE);
        vLoginRunning.setVisibility(View.VISIBLE);
    }
}
