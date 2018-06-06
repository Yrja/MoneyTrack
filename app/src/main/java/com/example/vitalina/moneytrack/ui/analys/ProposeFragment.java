package com.example.vitalina.moneytrack.ui.analys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.model.entities.Transaction;
import com.example.vitalina.moneytrack.ui.view.DiffView;

import java.util.Collection;
import java.util.Map;

public class ProposeFragment extends Fragment {
    Map.Entry<Transaction, Collection<Transaction>> entry;
    private TextView vTitle, vPrice, vLocation;
    private LinearLayout vAddContainer;
    public static ProposeFragment newInstance(Map.Entry<Transaction, Collection<Transaction>> entry) {
        ProposeFragment fragment = new ProposeFragment();
        fragment.entry = entry;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_proposals, container, false);
        vTitle = v.findViewById(R.id.vTitle);
        vPrice = v.findViewById(R.id.vPrice);
        vLocation = v.findViewById(R.id.vLocation);
        vAddContainer = v.findViewById(R.id.vAddContainer);

        vTitle.setText(entry.getKey().getDescription());
        vPrice.setText(String.valueOf(entry.getKey().getSum()));
        vLocation.setText(String.valueOf(entry.getKey().getLongitude()) +
                '\n' + String.valueOf(entry.getKey().getLatitude()));
        vLocation.setOnClickListener(vi->{
            Uri gmmIntentUri = Uri.parse("geo:"+entry.getKey().getLatitude()+","+entry.getKey().getLongitude());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
                getContext().startActivity(mapIntent);
            }
        });

        for (Transaction transaction : entry.getValue()){
            DiffView diffView = new DiffView(getActivity());
            diffView.setTransaction(transaction);
            vAddContainer.addView(diffView);
        }
        return v;
    }
}
