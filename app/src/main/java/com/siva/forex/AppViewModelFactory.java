package com.siva.forex;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.siva.forex.ui.RatesViewModel;

public class AppViewModelFactory implements ViewModelProvider.Factory {

    private static final String RATES_VIEW_MODEL = "com.siva.forex.ui.RatesViewModel";

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        ViewModel viewmodel = null;
        if (RATES_VIEW_MODEL.equalsIgnoreCase(modelClass.getCanonicalName())) {
            viewmodel = new RatesViewModel(ForexApp.getDatabase());
        }else{
            throw new IllegalArgumentException("Unknown model class " + modelClass.getCanonicalName());
        }
        return (T) viewmodel;
    }

}
