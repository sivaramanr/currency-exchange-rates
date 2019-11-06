package com.siva.forex.ui;

import android.os.Handler;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.siva.forex.Constants;
import com.siva.forex.db.AppDatabase;
import com.siva.forex.db.Rate;
import com.siva.forex.net.APIService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
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
                        apiService.getRates(Constants.CURRENCY_PAIRS)
                                .subscribeOn(Schedulers.io()).subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(Disposable d) {}

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                Timber.d(responseBody.toString());
                                try {
                                    JSONObject response = new JSONObject(responseBody.string());
                                    JSONObject rates = response.getJSONObject("rates");
                                    Iterator<String> keys = rates.keys();
                                    List<Rate> rateList = new ArrayList<>();
                                    while(keys.hasNext()){
                                        String key = keys.next();
                                        JSONObject crncy = rates.getJSONObject(key);
                                        Rate rate = new Rate();
                                        rate.pair = key;
                                        rate.rate = crncy.getDouble("rate");
                                        rateList.add(rate);
                                    }
                                    appDatabase.getRateDAO().store(rateList.toArray(new Rate[0]));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
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
