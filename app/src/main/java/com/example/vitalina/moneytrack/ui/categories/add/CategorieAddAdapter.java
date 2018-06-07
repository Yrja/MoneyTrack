package com.example.vitalina.moneytrack.ui.categories.add;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.ui.view.AbstractRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class CategorieAddAdapter extends RecyclerView.Adapter<CategoryIconHolder> {
    private List<SelectableItem<String>> items = new ArrayList<>();

    @NonNull
    @Override
    public CategoryIconHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryIconHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_icon, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryIconHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<String> items) {
        Observable.fromIterable(items)
                .map(str -> new SelectableItem(str, false))
                .toList()
                .subscribe(next -> {
                    items.clear();

                });
    }
}
