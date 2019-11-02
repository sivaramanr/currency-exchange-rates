package com.siva.forex.ui;

import android.os.Bundle;
import android.os.StrictMode;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.siva.forex.AppViewModelFactory;
import com.siva.forex.R;
import com.siva.forex.db.Rate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import timber.log.Timber;

public class MainActivity extends DaggerAppCompatActivity{


    @Inject AppViewModelFactory viewmodelFactory;

    @Inject RateUpdaterJob updaterJob;

    private RatesViewModel viewModel;

    private CurrencyRatesAdapter adapter;

    private RecyclerView recyclerView;

    private List<Rate> rateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        viewModel = viewmodelFactory.create(RatesViewModel.class);
        rateList = new ArrayList<>();
        viewModel.getRateList().observe(this, new Observer<List<Rate>>() {
            @Override
            public void onChanged(List<Rate> rates) {
                Timber.d("Exchange rates updated");
                rateList.clear();
                rateList.addAll(rates);
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new CurrencyRatesAdapter(rateList);
        getLifecycle().addObserver(updaterJob);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
