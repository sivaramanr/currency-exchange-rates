package com.siva.forex.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.siva.forex.R;
import com.siva.forex.db.Rate;

import java.util.List;

public class CurrencyRatesAdapter extends RecyclerView.Adapter<CurrencyRatesAdapter.ViewHolder> {

    private List<Rate> rateList;

    public CurrencyRatesAdapter(List<Rate> rateList){
        this.rateList = rateList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_currency, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (rateList.size()!=0){
            Rate rate = rateList.get(position);
            holder.currencyPair.setText(rate.pair);
            holder.conversionRate.setText(Double.toString(rate.rate));
        }
    }

    @Override
    public int getItemCount() {
        return rateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView currencyPair;

        final TextView conversionRate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            currencyPair = itemView.findViewById(R.id.currencyPair);
            conversionRate = itemView.findViewById(R.id.conversionRate);
        }

    }

}
