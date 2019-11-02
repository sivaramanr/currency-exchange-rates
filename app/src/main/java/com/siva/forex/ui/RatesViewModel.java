package com.siva.forex.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.siva.forex.db.AppDatabase;
import com.siva.forex.db.Rate;
import com.siva.forex.db.RateDAO;

import java.util.List;

public class RatesViewModel extends ViewModel {

    private MutableLiveData<List<Rate>> rateList;

    private RateDAO rateDAO;

    public RatesViewModel(AppDatabase appDatabase){
        rateDAO = appDatabase.getRateDAO();
    }

    public LiveData<List<Rate>> getRateList() {
        return rateDAO.selectAll();
    }

    public void setRateList(MutableLiveData<List<Rate>> rateList) {
        this.rateList = rateList;
    }
}
