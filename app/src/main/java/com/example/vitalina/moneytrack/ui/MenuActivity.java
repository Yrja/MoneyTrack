package com.example.vitalina.moneytrack.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.model.entities.User;
import com.example.vitalina.moneytrack.ui.auth.SignUpFragment;
import com.example.vitalina.moneytrack.ui.categories.CategorieFragment;
import com.example.vitalina.moneytrack.ui.profile.ProfileFragment;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        if (getIntent().getExtras() != null && !getIntent().getExtras().isEmpty()) {
            switch (getIntent().getIntExtra("action", 0)) {
                case SignUpFragment.USER_CREATED:
                    goToProfile((User) getIntent().getSerializableExtra("user"));
                    break;
                default:
                    goToCategories();
                    break;
            }
        } else {
            goToCategories();
        }
        ((NavigationView) findViewById(R.id.navigation))
                .setNavigationItemSelectedListener(this);
    }

    public void goToProfile(User user) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, ProfileFragment.newInstance(user))
                .commit();
    }
    public void goToCategories() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container,new CategorieFragment())
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_profile:
                goToProfile(null);
                break;
            case R.id.menu_categories:
                goToCategories();
                break;
        }
        return true;
    }
}
