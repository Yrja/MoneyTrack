package com.example.vitalina.moneytrack.ui.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class  AbstractRecyclerAdapter <T, V extends BaseHolder<T>> extends RecyclerView.Adapter<V> {
    private ArrayList<T> items = new ArrayList<>();

    public void setItems(List<T> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
