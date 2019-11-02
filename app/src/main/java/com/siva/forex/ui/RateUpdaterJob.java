package com.siva.forex.ui;

import android.os.Handler;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.siva.forex.db.AppDatabase;
import com.siva.forex.db.Rate;
import com.siva.forex.net.APIService;
import com.siva.forex.net.EURGBP;
import com.siva.forex.net.ExchangeRates;
import com.siva.forex.net.USDEUR;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class RateUpdaterJob implements LifecycleObserver {

    @Inject
    APIService apiService;

    @Inject
    AppDatabase appDatabase;

    private Timer timer;

    private Handler handler;

    private TimerTask timerTask;

    @Inject
    public RateUpdaterJob(){
        handler = new Handler();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void start(){
        Timber.d("start");
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        apiService.getRates("EURGBP,USDEUR").subscribe(new Observer<ExchangeRates>() {
                            @Override
                            public void onSubscribe(Disposable d) {}

                            @Override
                            public void onNext(ExchangeRates exchangeRates) {
                                ExchangeRates.Rates rates = exchangeRates.getRates();
                                EURGBP eurgbp = rates.getEURGBP();
                                Rate rate1 = new Rate();
                                rate1.pair = "EURGBP";
                                rate1.rate = eurgbp.getRate();
                                rate1.timestamp = eurgbp.getTimestamp();
                                USDEUR usdeur = rates.getUSDEUR();
                                Rate rate2 = new Rate();
                                rate2.pair = "USDEUR";
                                rate2.rate = usdeur.getRate();
                                rate2.timestamp = usdeur.getTimestamp();
                                appDatabase.getRateDAO().store(rate1, rate2);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Timber.d(e);
                            }

                            @Override
                            public void onComplete() {}
                        });
                    }
                });
            }
        };
        timer.schedule(timerTask, 500, 7000);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void stop(){
        Timber.d("stop");
        if (timer!=null) {
            timer.cancel();
            timer = null;
        }
    }

}
