package com.example.vitalina.moneytrack.ui.categories.detail.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.model.entities.Transaction;
import com.example.vitalina.moneytrack.ui.view.BaseHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

        vDescription.setText(transaction.getDescription());
        vLocation.setText(transaction.getLongitude() + " " + transaction.getLatitude());
        vLocation.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:" + transaction.getLatitude() + "," + transaction.getLongitude());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(itemView.getContext().getPackageManager()) != null) {
                itemView.getContext().startActivity(mapIntent);
            }
        });
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        vDate.setText(format.format(new Date(transaction.getTime())));
    }
}
