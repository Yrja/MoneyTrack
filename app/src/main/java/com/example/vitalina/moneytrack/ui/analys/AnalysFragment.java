package com.example.vitalina.moneytrack.ui.analys;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.model.AnalysManager;
import com.example.vitalina.moneytrack.model.entities.Analys;
import com.example.vitalina.moneytrack.model.entities.Categorie;
import com.example.vitalina.moneytrack.model.entities.SpentByCategorie;
import com.example.vitalina.moneytrack.model.entities.Transaction;
import com.example.vitalina.moneytrack.ui.analys.presenter.AnalysPresenter;
import com.example.vitalina.moneytrack.ui.analys.presenter.AnalysView;
import com.example.vitalina.moneytrack.ui.view.DisableViewPager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.listener.PieRadarChartTouchListener;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class AnalysFragment extends Fragment implements AnalysView{
    private PieChart pieChart;
    private AnalysManager analysManager;
    private TextView vTitle;
    private AnalysPresenter analysPresenter;
    private DisableViewPager vPager;
    private ProposeAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmant_analys, container, false);
        pieChart = view.findViewById(R.id.vPieChart);
        vTitle = view.findViewById(R.id.vTitle);
        vPager = view.findViewById(R.id.vPager);
        analysManager = AnalysManager.getInstance();
        analysPresenter = new AnalysPresenter(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Analys analys = analysManager.getAnalysResult();

        HashMap<Categorie, SpentByCategorie> map = analys.getSpendByCategorie();
        List<PieEntry> pieEntries = new ArrayList<>();
        for (Map.Entry<Categorie, SpentByCategorie> entry : map.entrySet()) {
            // PieEntry pieEntry = new PieEntry((float) , entry.getKey().getName());
            PieEntry pieEntry = new PieEntry(Math.abs((float) entry.getKey().getSpendByMonth()), entry.getKey().getName());
            pieEntries.add(pieEntry);

        }

        List<Integer> colors = new ArrayList<Integer>();
        colors.add(ContextCompat.getColor(getActivity(), R.color.chartFirst));
        colors.add(ContextCompat.getColor(getActivity(), R.color.chartSecond));
        colors.add(ContextCompat.getColor(getActivity(), R.color.chartThird));
        colors.add(ContextCompat.getColor(getActivity(), R.color.chartFourth));
        colors.add(ContextCompat.getColor(getActivity(), R.color.colorAccentSecondary));
        PieDataSet dataSet = new PieDataSet(pieEntries, "Spend by month");
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setCenterText("Spend by month");
        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.getLegend().setTextColor(Color.WHITE);
        Description description = new Description();
        description.setTextColor(Color.WHITE);
        description.setText("Spent by last 30 day");
        pieChart.setDescription(description);

        pieChart.setData(new PieData(dataSet));
        pieChart.invalidate();


        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Map.Entry<Categorie, SpentByCategorie> entry = analys.getCategorieByName(((PieEntry) e).getLabel());
                if (entry!=null){
                    vTitle.setText("The most you spend on \n " + entry.getValue().getCategorie() + "\n" + entry.getValue().getSum());
                    analysPresenter.startAnalys(entry.getKey());
                    vPager.setPagingEnabled(false);
                }

            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    @Override
    public void displayAnalys(Multimap<Transaction, Transaction> proposals) {
        adapter = new ProposeAdapter(proposals, ((AppCompatActivity)getActivity()).getSupportFragmentManager());
        vPager.setAdapter(adapter);
        vPager.invalidate();
        adapter.notifyDataSetChanged();
        vPager.setPagingEnabled(true);
    }
}
