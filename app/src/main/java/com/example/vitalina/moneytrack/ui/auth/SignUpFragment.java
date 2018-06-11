package com.example.vitalina.moneytrack.ui.auth;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.example.vitalina.moneytrack.data.CredentialsPreference;
import com.example.vitalina.moneytrack.model.entities.User;
import com.example.vitalina.moneytrack.ui.MainActivity;
import com.example.vitalina.moneytrack.ui.MenuActivity;
import com.example.vitalina.moneytrack.ui.auth.presenter.SignUpPresenter;
import com.example.vitalina.moneytrack.ui.auth.presenter.SignUpView;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpFragment extends Fragment implements SignUpView, TextWatcher {
    private EditText vEmail, vPassword, vConfirmPassword;
    private CircleImageView vAvatar;
    private ImageView vCamera, vGallery;
    private Button vConfirm, vCancel;
    private SignUpPresenter signUpPresenter;
    private boolean isValid;
    private final static int TAKE_FROM_CAMERA_REQUEST = 1;
    private final static int CAMERA_PERMISSION = 2;
    private final static int TAKE_FROM_GALLERY_REQUEST = 3;
    private final static int READ_PERMISSION = 4;

    public static final int USER_CREATED = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_account, container, false);
        vEmail = v.findViewById(R.id.authorization_email);
        vPassword = v.findViewById(R.id.create_account_password);
        vConfirmPassword = v.findViewById(R.id.create_account_repeat_password);
        vAvatar = v.findViewById(R.id.profile_image);
        vCamera = v.findViewById(R.id.vCamera);
        vGallery = v.findViewById(R.id.vGallery);
        vCancel = v.findViewById(R.id.create_account_cancel);
        vConfirm = v.findViewById(R.id.create_account_confirm);

        vCamera.setOnClickListener((view) -> {
            takeFromCamera();
        });
        vGallery.setOnClickListener((view) -> {
            takeFromGallery();
        });

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vConfirm.setOnClickListener(view12 -> {
            if (vPassword.getText().toString().isEmpty() || vConfirmPassword.getText().toString().isEmpty()
                    || vEmail.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "Fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                if (!vPassword.getText().toString().equals(vConfirmPassword.getText().toString())) {
                    Toast.makeText(getActivity(), "Passwords does`nt mismatch", Toast.LENGTH_SHORT).show();
                } else {
                    signUpPresenter.signUp(vEmail.getText().toString(), vPassword.getText().toString());
                }
            }
        });
        vCancel.setOnClickListener(view1 -> ((MainActivity) getActivity()).goToAuth());

        signUpPresenter = new SignUpPresenter(this, new CredentialsPreference(getActivity()));

    }

    @Override
    public void onSignUp(User user) {
        Intent intent = new Intent(getActivity(), MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("action", USER_CREATED);
        intent.putExtra("user",user);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onSignUpFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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

    private void takeFromCamera() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, SignUpFragment.TAKE_FROM_CAMERA_REQUEST);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                    SignUpFragment.CAMERA_PERMISSION);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case SignUpFragment.TAKE_FROM_CAMERA_REQUEST:
                    Bitmap img = (Bitmap) data.getExtras().get("data");
                    vAvatar.setImageBitmap(img);
                    signUpPresenter.uploadAvatar(img);
                    break;
                case SignUpFragment.TAKE_FROM_GALLERY_REQUEST:
                    if (data != null && data.getData() != null) {
                        Uri imgUri = data.getData();
                        Picasso.get().load(imgUri).into(vAvatar);
                        signUpPresenter.uploadAvatar(imgUri);
                    }
                    break;
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case SignUpFragment.CAMERA_PERMISSION:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    takeFromCamera();
                }
                break;
            case SignUpFragment.READ_PERMISSION:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED)
                    takeFromGallery();
                break;
        }

    }

    private void takeFromGallery() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SignUpFragment.TAKE_FROM_GALLERY_REQUEST);
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, SignUpFragment.READ_PERMISSION);
        }
    }
}
