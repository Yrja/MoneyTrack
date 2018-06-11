package com.example.vitalina.moneytrack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.data.AnalysFirestore;
import com.example.vitalina.moneytrack.data.CredentialsPreference;
import com.example.vitalina.moneytrack.model.AnalysManager;
import com.example.vitalina.moneytrack.model.entities.Categorie;
import com.example.vitalina.moneytrack.model.entities.Credentials;
import com.example.vitalina.moneytrack.model.entities.Transaction;
import com.example.vitalina.moneytrack.model.entities.User;
import com.example.vitalina.moneytrack.ui.analys.AnalysFragment;
import com.example.vitalina.moneytrack.ui.auth.SignUpFragment;
import com.example.vitalina.moneytrack.ui.categories.CategorieFragment;
import com.example.vitalina.moneytrack.ui.profile.ProfileFragment;

import java.util.HashMap;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout vDrawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        vDrawer = findViewById(R.id.vDrawerLayout);

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
                .replace(R.id.container, new CategorieFragment())
                .commit();
    }

    public void goToAnalys() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new AnalysFragment())
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
            case R.id.menu_analys:
                goToAnalys();
                break;
            case R.id.menu_logout:
                new CredentialsPreference(this).setCredentials(new Credentials(null,null));
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        vDrawer.closeDrawers();
        return true;
    }
}
