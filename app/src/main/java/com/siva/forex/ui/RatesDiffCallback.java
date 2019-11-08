package com.siva.forex.ui;

import androidx.recyclerview.widget.DiffUtil;

import com.siva.forex.db.Rate;

import java.util.List;

public class RatesDiffCallback extends DiffUtil.Callback{

    private List<Rate> oldRateList;

    private List<Rate> newRateList;

    public RatesDiffCallback(List<Rate> oldRateList, List<Rate> newRateList){
        this.oldRateList = oldRateList;
        this.newRateList = newRateList;
    }

    @Override
    public int getOldListSize() {
        return oldRateList.size();
    }

    @Override
    public int getNewListSize() {
        return newRateList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Rate oldRate = oldRateList.get(oldItemPosition);
        Rate newRate = newRateList.get(newItemPosition);
        if (oldRate.pair.equalsIgnoreCase(newRate.pair)){
            return true;
        }
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Rate oldRate = oldRateList.get(oldItemPosition);
        Rate newRate = newRateList.get(newItemPosition);
        if (oldRate.pair.equalsIgnoreCase(newRate.pair) &&
                oldRate.rate == newRate.rate){
            return true;
        }
        return false;
    }

}
