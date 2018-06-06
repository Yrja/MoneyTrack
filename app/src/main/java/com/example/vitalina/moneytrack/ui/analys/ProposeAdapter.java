package com.example.vitalina.moneytrack.ui.analys;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.vitalina.moneytrack.model.entities.Transaction;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Map;

public class ProposeAdapter extends FragmentStatePagerAdapter {
    private Multimap<Transaction, Transaction> multimap;


    public ProposeAdapter(Multimap<Transaction, Transaction> multimap, FragmentManager fm) {
        super(fm);
        this.multimap = multimap;
    }

    @Override
    public Fragment getItem(int position) {
        return ProposeFragment.newInstance((Map.Entry<Transaction, Collection<Transaction>>) multimap.asMap().entrySet().toArray()[position]);
    }

    @Override
    public int getCount() {
        return multimap.asMap().entrySet().size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
