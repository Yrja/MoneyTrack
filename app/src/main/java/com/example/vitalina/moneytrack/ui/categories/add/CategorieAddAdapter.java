package com.example.vitalina.moneytrack.ui.categories.add;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.vitalina.moneytrack.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class CategorieAddAdapter extends RecyclerView.Adapter<CategoryIconHolder> implements OnItemClick{
    private List<SelectableItem<String>> items = new ArrayList<>();
    private int prevActivePos;

    @NonNull
    @Override
    public CategoryIconHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryIconHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_icon, parent, false), this);
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
        Disposable d =Observable.fromIterable(items)
                .map(str -> new SelectableItem(str, false))
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(next -> {
                    CategorieAddAdapter.this.items.clear();
                    CategorieAddAdapter.this.items.addAll((List)next);
                    notifyDataSetChanged();
                });
    }
    public String getSelectedItem(){
        return items.get(prevActivePos).item;
    }
    @Override
    public void onItemClick(int pos) {
        if (prevActivePos != -1 && prevActivePos!=pos) {
            SelectableItem<String> item = items.get(prevActivePos);
            item.setSelected(false);
            items.set(prevActivePos,item);
            notifyItemChanged(prevActivePos);
        }
        items.get(pos).setSelected(true);
        prevActivePos = pos;
    }
}
