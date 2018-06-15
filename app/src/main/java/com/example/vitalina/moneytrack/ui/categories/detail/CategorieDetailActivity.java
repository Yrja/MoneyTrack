package com.example.vitalina.moneytrack.ui.categories.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.model.entities.Categorie;
import com.example.vitalina.moneytrack.model.entities.Transaction;
import com.example.vitalina.moneytrack.ui.categories.detail.adapter.TransactionAdapter;
import com.example.vitalina.moneytrack.ui.categories.detail.presenter.TransactionPresenter;
import com.example.vitalina.moneytrack.ui.categories.detail.presenter.TransactionView;
import com.example.vitalina.moneytrack.ui.categories.presenter.CategoriesPresenter;
import com.example.vitalina.moneytrack.ui.categories.presenter.CategoriesView;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class CategorieDetailActivity extends AppCompatActivity implements TransactionView, View.OnClickListener {
    private HorizontalCalendarView vCalendar;
    private RecyclerView vList;
    private TransactionPresenter presenter;
    private Categorie mCurrentCategorie;
    private TransactionAdapter adapter;
    private CardView vAddTransaction;
    private LinearLayout vAddContainer;
    private TextView vCancelAdd;
    private CheckBox vLocationCheckBox;

    private EditText vSum, vNotes;

    private TextView vDelete;

    private boolean isAdding;
    private PublishSubject<Boolean> addingSubject = PublishSubject.create();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie_detail);
        vCalendar = findViewById(R.id.calendarView);
        vCancelAdd = findViewById(R.id.vCancelAdd);
        vLocationCheckBox = findViewById(R.id.vLocationCheckBox);
        vCancelAdd.setOnClickListener(this);
        vAddContainer = findViewById(R.id.vAddContainer);
        vNotes = findViewById(R.id.vNote);
        vSum = findViewById(R.id.vSum);
        vList = findViewById(R.id.vList);
        vDelete = findViewById(R.id.vDelete);
        //vEdit = findViewById(R.id.vEdit);

        vList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionAdapter();
        vList.setAdapter(adapter);
        vAddContainer.setVisibility(View.INVISIBLE);
        vAddContainer.animate()
                .translationX(-vAddContainer.getWidth())
                .setDuration(500);
        vAddTransaction = findViewById(R.id.vAddTransaction);

        vAddTransaction.setOnClickListener(v -> {
            vAddContainer.setVisibility(View.VISIBLE);
            if (isAdding) {
                presenter.makeTransaction(mCurrentCategorie, new Transaction(new Date().getTime(),
                        vNotes.getText().toString(), Long.parseLong(vSum.getText().toString()),
                        0, 0),vLocationCheckBox.isChecked());
            } else {
                setAdding(!isAdding);
            }
        });

        if (getIntent().getExtras() != null && !getIntent().getExtras().isEmpty()) {
            mCurrentCategorie = (Categorie) getIntent().getSerializableExtra("categorie");
            Picasso.get().load(mCurrentCategorie.getIcon()).into(
                    (ImageView) findViewById(R.id.categorieAvatar));
            ((EditText)findViewById(R.id.categorieName)).setText(mCurrentCategorie.getName());
            vDelete.setOnClickListener(v -> {
                presenter.deleteCategory(mCurrentCategorie);
            });
        }

        presenter = new TransactionPresenter(this, this);
        initCalendar();

        Disposable d = addingSubject.subscribe(next -> {
            if (next) {
                vList.animate()
                        .translationX(vList.getWidth())
                        .setDuration(500);
                vAddContainer.animate()
                        .translationX(0)
                        .setDuration(500);
            } else {
                vList.animate()
                        .translationX(0)
                        .setDuration(500);
                vAddContainer.animate()
                        .translationX(-vAddContainer.getWidth())
                        .setDuration(500);
            }
        });
    }

    public void setAdding(boolean adding) {
        isAdding = adding;
        addingSubject.onNext(adding);
    }

    private void initCalendar() {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);
        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .datesNumberOnScreen(5)
                .range(startDate, endDate).build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                if (DateUtils.isToday(date.getTime().getTime())) {
                    vAddTransaction.setVisibility(View.VISIBLE);
                } else {
                    vAddTransaction.setVisibility(View.GONE);
                }
                presenter.loadTransaction(mCurrentCategorie, date.getTime());
            }
        });
        presenter.loadTransaction(mCurrentCategorie, new Date());
    }

    @Override
    public void onTransactionSuccess(Transaction transaction) {
        adapter.addItems(transaction);
        setAdding(false);
        Snackbar.make(vCalendar, "success", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionFailed(String message) {
        Snackbar.make(vCalendar, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionsLoaded(List<Transaction> transactions) {
        adapter.setItems(transactions);
    }

    @Override
    public void onDeleteSuccess() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vCancelAdd:
                setAdding(false);
                break;
        }
    }
}
