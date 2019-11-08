package com.siva.forex.ui;

import android.os.Bundle;
import android.os.StrictMode;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
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

    private List<Rate> rateList;

    private CurrencyRatesAdapter adapter;

    private RecyclerView recyclerView;

    private RatesDiffCallback ratesDiffCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        rateList = new ArrayList<>();
        viewModel = viewmodelFactory.create(RatesViewModel.class);
        viewModel.getRateList().observe(this, new Observer<List<Rate>>() {
            @Override
            public void onChanged(List<Rate> newRates) {
                Timber.d("Exchange Rates updated");
                if (rateList.size()==0){
                    rateList.addAll(newRates);
                    adapter.notifyDataSetChanged();
                }else{
                    ratesDiffCallback = new RatesDiffCallback(adapter.getRateList(), newRates);
                    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(ratesDiffCallback);
                    adapter.setRateList(newRates);
                    diffResult.dispatchUpdatesTo(adapter);
                }
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
