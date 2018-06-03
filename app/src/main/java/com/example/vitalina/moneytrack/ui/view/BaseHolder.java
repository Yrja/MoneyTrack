package com.example.vitalina.moneytrack.ui.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {

    public BaseHolder(View itemView) {
        super(itemView);
    }
    public abstract void bind(T t);
}
