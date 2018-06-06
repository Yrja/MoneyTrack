package com.example.vitalina.moneytrack.ui.analys.presenter;

import com.example.vitalina.moneytrack.model.entities.Transaction;
import com.google.common.collect.Multimap;

import java.util.HashMap;

public interface AnalysView {
    void displayAnalys(Multimap<Transaction,Transaction> proposals);
}
