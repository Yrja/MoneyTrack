package com.example.vitalina.moneytrack.ui.categories.detail.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.model.entities.Transaction;
import com.example.vitalina.moneytrack.ui.view.AbstractRecyclerAdapter;

public class TransactionAdapter extends AbstractRecyclerAdapter<Transaction, TransactionHolder> {
    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_transaction, parent, false));
    }
}
