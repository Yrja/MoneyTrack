package com.example.vitalina.moneytrack.ui.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.model.entities.Transaction;

public class DiffView extends ConstraintLayout{
    private TextView vPrice,vLocation;
    public DiffView(Context context) {
        super(context);
        init();
    }

    public DiffView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DiffView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        View v = LayoutInflater.from(getContext()).inflate(R.layout.item_transaction_propose,this);
        vPrice = v.findViewById(R.id.vPrice);
        vLocation = v.findViewById(R.id.vLocation);
    }
    public void setTransaction(Transaction transaction){
        vPrice.setText(String.valueOf(transaction.getSum()));
        vLocation.setText(String.valueOf(transaction.getLongitude() + "\n" + transaction.getLatitude()));
        vLocation.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:"+transaction.getLatitude()+","+transaction.getLongitude());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
                getContext().startActivity(mapIntent);
            }
        });
    }
}
