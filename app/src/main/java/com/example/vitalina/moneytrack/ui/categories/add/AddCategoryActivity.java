package com.example.vitalina.moneytrack.ui.categories.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.model.entities.Categorie;
import com.example.vitalina.moneytrack.ui.categories.add.presenter.AddCategoriePresenter;
import com.example.vitalina.moneytrack.ui.categories.add.presenter.AddCategorieView;

public class AddCategoryActivity extends AppCompatActivity implements AddCategorieView {
    private RecyclerView vList;
    private CategorieAddAdapter adapter;
    private FrameLayout vAdd;

    private TextView vAddText;
    private ProgressBar vAddProgress;

    private EditText vCategoryName, vInitSum;
    private AddCategoriePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);
        vList = findViewById(R.id.recyclerView);
        vList.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new CategorieAddAdapter();
        vList.setAdapter(adapter);
        vAdd = findViewById(R.id.button);
        vCategoryName = findViewById(R.id.vCategoryName);
        vInitSum = findViewById(R.id.vInitialSum);
        vAddText = findViewById(R.id.vAddText);
        vAddProgress = findViewById(R.id.vAddProgress);

        vAdd.setOnClickListener(v -> {
            vAdd.setEnabled(false);
            vAddText.setVisibility(View.INVISIBLE);
            vAddProgress.setVisibility(View.VISIBLE);


            String name = vCategoryName.getText().toString();
            Float initSum = Float.parseFloat(vInitSum.getText().toString());
            String icon  = adapter.getSelectedItem();
            presenter.addCategorie(new Categorie(0,name,icon,initSum, true));
        });

        adapter.setItems(IconsHolder.getIcons());
        presenter = new AddCategoriePresenter(this);
    }

    @Override
    public void onAddSuccess() {
        finish();
    }

    @Override
    public void onAddFailed() {
        vAdd.setEnabled(true);
        vAddText.setVisibility(View.VISIBLE);
        vAddProgress.setVisibility(View.INVISIBLE);
    }
}
