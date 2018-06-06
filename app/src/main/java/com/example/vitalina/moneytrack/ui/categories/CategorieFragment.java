package com.example.vitalina.moneytrack.ui.categories;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.model.entities.Categorie;
import com.example.vitalina.moneytrack.ui.categories.presenter.CategoriesPresenter;
import com.example.vitalina.moneytrack.ui.categories.presenter.CategoriesView;

import java.util.List;

public class CategorieFragment extends Fragment implements CategoriesView {
    private RecyclerView vList;
    private CategoriesAdapter adapter;
    private CategoriesPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_categories,container,false);
        vList = v.findViewById(R.id.vCategoriesList);
        vList.setLayoutManager(new GridLayoutManager(getActivity(),3));
        adapter = new CategoriesAdapter();
        vList.setAdapter(adapter);
        presenter = new CategoriesPresenter(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getCategories();
    }

    @Override
    public void displayCategories(List<Categorie> categories) {
        adapter.setItems(categories);
    }

    @Override
    public void errorLoadingCategories(String message) {
        Snackbar.make(vList,message,Snackbar.LENGTH_SHORT).show();
    }


}
