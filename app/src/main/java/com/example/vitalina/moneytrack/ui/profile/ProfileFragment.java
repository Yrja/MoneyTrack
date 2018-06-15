package com.example.vitalina.moneytrack.ui.profile;

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
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.model.entities.User;
import com.example.vitalina.moneytrack.ui.auth.SignUpFragment;
import com.example.vitalina.moneytrack.ui.profile.presenter.ProfilePresenter;
import com.example.vitalina.moneytrack.ui.profile.presenter.ProfileView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements ProfileView{
    private User user;
    private CircleImageView vAvatar;
    private TextView vCongratulations;
    private EditText vFirstName, vLastName;
    private Button vPrimaryButton;
    private ProfilePresenter profilePresenter;
    private LinearLayout vActionContainer;
    private Button vEditButton, vCancelButton;
    private ImageView vGallery, vCamera;
    private final static int TAKE_FROM_CAMERA_REQUEST = 1;
    private final static int CAMERA_PERMISSION = 2;
    private final static int TAKE_FROM_GALLERY_REQUEST = 3;
    private final static int READ_PERMISSION = 4;

    public static final int USER_CREATED = 1;

    public static ProfileFragment newInstance(User user){
        ProfileFragment fragment = new ProfileFragment();
        fragment.user = user;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile,container,false);
        vAvatar = v.findViewById(R.id.vAvatar);
        vCongratulations = v.findViewById(R.id.vCongratulations);

        vFirstName = v.findViewById(R.id.vFirstName);
        vLastName = v.findViewById(R.id.vLastName);
        vPrimaryButton = v.findViewById(R.id.vPrimaryBtn);
        vActionContainer = v.findViewById(R.id.vActionContainer);
        vEditButton = v.findViewById(R.id.vSaveBtn);
        vCancelButton = v.findViewById(R.id.vCancelBtn);
        vCamera= v.findViewById(R.id.vCamera);
        vGallery = v.findViewById(R.id.vGallery);

        profilePresenter = new ProfilePresenter(this);

        if (user!=null){
            // is new register user
            vCongratulations.setVisibility(View.VISIBLE);
            displayProfile(user);
        } else {
            profilePresenter.getUser();
        }
        vFirstName.setInputType(InputType.TYPE_NULL);
        vLastName.setInputType(InputType.TYPE_NULL);
        vPrimaryButton.setOnClickListener(v1 -> {
            vPrimaryButton.setVisibility(View.GONE);
            vActionContainer.setVisibility(View.VISIBLE);
            vFirstName.setInputType(InputType.TYPE_CLASS_TEXT);
            vLastName.setInputType(InputType.TYPE_CLASS_TEXT);
        });
        vEditButton.setOnClickListener(v13 -> {
            String firstName = vFirstName.getText().toString();
            String lastName = vLastName.getText().toString();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            profilePresenter.updateUser(user);
            vPrimaryButton.setVisibility(View.VISIBLE);
            vActionContainer.setVisibility(View.GONE);
            vFirstName.setInputType(InputType.TYPE_NULL);
            vLastName.setInputType(InputType.TYPE_NULL);
        });
        vCancelButton.setOnClickListener(v12 -> {
            vPrimaryButton.setVisibility(View.VISIBLE);
            vActionContainer.setVisibility(View.GONE);
            vFirstName.setInputType(InputType.TYPE_NULL);
            vLastName.setInputType(InputType.TYPE_NULL);

        });

        vCamera.setOnClickListener((view) -> {
            takeFromCamera();
        });
        vGallery.setOnClickListener((view) -> {
            takeFromGallery();
        });
        return v;
    }
    private void takeFromCamera() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, TAKE_FROM_CAMERA_REQUEST);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_FROM_CAMERA_REQUEST:
                    Bitmap img = (Bitmap) data.getExtras().get("data");
                    vAvatar.setImageBitmap(img);
                    profilePresenter.updateAvatar(img,user);
                    break;
                case TAKE_FROM_GALLERY_REQUEST:
                    if (data != null && data.getData() != null) {
                        Uri imgUri = data.getData();
                        Picasso.get().load(imgUri).into(vAvatar);
                        profilePresenter.updateAvatar(imgUri,user);
                    }
                    break;
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    takeFromCamera();
                }
                break;
            case READ_PERMISSION:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED)
                    takeFromGallery();
                break;
        }

    }

    private void takeFromGallery() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, TAKE_FROM_GALLERY_REQUEST);
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PERMISSION);
        }
    }
    @Override
    public void displayProfile(User user) {
        this.user = user;
        if (user.getAvatar()!=null) {
            Picasso.get()
                    .load(user.getAvatar())
                    .into(vAvatar);
        }
        vFirstName.setText(user.getFirstName()!=null ? user.getFirstName() : "First name");
        vLastName.setText(user.getLastName()!=null ? user.getLastName() : "Last name");
    }

    @Override
    public void userLoadError(String message) {
        Snackbar.make(vAvatar,message,Snackbar.LENGTH_SHORT).show();
    }
}
