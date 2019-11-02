package com.siva.forex;

import androidx.room.Room;

import com.siva.forex.db.AppDatabase;
import com.siva.forex.di.AppComponent;
import com.siva.forex.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

import static com.siva.forex.Constants.DATABASE_NAME;

public class ForexApp extends DaggerApplication {

    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
        database =  Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();

    }

    public static AppDatabase getDatabase() {
        return database;
    }

    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }

}
