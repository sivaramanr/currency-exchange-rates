package com.siva.forex.di;

import com.siva.forex.AppViewModelFactory;
import com.siva.forex.ForexApp;
import com.siva.forex.db.AppDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {

    @Provides
    static AppViewModelFactory provideAppViewModelFactory() {
        return new AppViewModelFactory();
    }

    @Provides
    static AppDatabase provideAppDatabase() {
        return ForexApp.getDatabase();
    }

}
