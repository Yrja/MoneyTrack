package com.example.vitalina.moneytrack.ui.analys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TransactionProposeAdapter extends FragmentPagerAdapter {
    public TransactionProposeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new TransactionProposeFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
