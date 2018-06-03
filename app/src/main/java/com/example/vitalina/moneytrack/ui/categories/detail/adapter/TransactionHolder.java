package com.example.vitalina.moneytrack.ui.categories.detail.adapter;

import android.view.View;
import android.widget.TextView;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.model.entities.Transaction;
import com.example.vitalina.moneytrack.ui.view.BaseHolder;

public class TransactionHolder extends BaseHolder<Transaction> {
    private TextView vSum, vDate, vDescription, vLocation;
    public TransactionHolder(View itemView) {
        super(itemView);
        vSum = itemView.findViewById(R.id.vSum);
        vDate = itemView.findViewById(R.id.vDate);
        vDescription = itemView.findViewById(R.id.vDescription);
        vLocation = itemView.findViewById(R.id.vLocation);
    }

    @Override
    public void bind(Transaction transaction) {
        vSum.setText(String.valueOf(transaction.getSum()));
        vDate.setText(String.valueOf(transaction.getTime()));
        vDescription.setText(transaction.getDescription());
        vLocation.setText(transaction.getLongitude()+" "+ transaction.getLatitude());
    }
}
