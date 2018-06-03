package com.example.vitalina.moneytrack.ui.profile;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.model.entities.User;
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

        profilePresenter = new ProfilePresenter(this);

        if (user!=null){
            // is new register user
            vCongratulations.setVisibility(View.VISIBLE);
            displayProfile(user);
        } else {
            profilePresenter.getUser();
        }
        return v;
    }

    @Override
    public void displayProfile(User user) {
        Picasso.get()
                .load(user.getAvatar())
                .into(vAvatar);
    }

    @Override
    public void userLoadError(String message) {
        Snackbar.make(vAvatar,message,Snackbar.LENGTH_SHORT).show();
    }
}
