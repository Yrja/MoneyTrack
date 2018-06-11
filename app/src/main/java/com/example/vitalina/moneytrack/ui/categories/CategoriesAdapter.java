package com.example.vitalina.moneytrack.ui.categories;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.model.entities.Categorie;
import com.example.vitalina.moneytrack.ui.view.AbstractRecyclerAdapter;

import java.util.List;

public class CategoriesAdapter extends AbstractRecyclerAdapter<Categorie, CategoriesHolder> {
    public static final int ITEM = 0;
    public static  final int ADD_ITEM = 1;
    @NonNull
    @Override
    public CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM)
        return new CategoriesHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_spend_categories, parent, false),viewType);
        else
            return new CategoriesHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_add_categorie, parent, false),viewType);
    }

    @Override
    public void setItems(List<Categorie> items) {
        items.add(new Categorie(-1,"",R.drawable.ic_add+"",0f));
        super.setItems(items);
    }

    @Override
    public int getItemViewType(int position) {
        if (position== getItemCount()-1){
            return ADD_ITEM;
        } else {
            return ITEM;
        }
    }
}
