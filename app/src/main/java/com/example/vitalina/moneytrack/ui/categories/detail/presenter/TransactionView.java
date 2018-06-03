package com.example.vitalina.moneytrack.ui.categories.detail.presenter;

import com.example.vitalina.moneytrack.model.entities.Transaction;

import java.util.List;

public interface TransactionView  {
    void onTransactionSuccess();
    void onTransactionFailed(String message);
    void onTransactionsLoaded(List<Transaction> transactions);
}
