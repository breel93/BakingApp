package com.example.breezil.bakingapp.di;


import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.breezil.bakingapp.api.BakingApi;
import com.example.breezil.bakingapp.db.AppDatabase;
import com.example.breezil.bakingapp.utils.helpers.LiveDataCallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
class ApplicationModule {

    @Singleton
    @Provides
    BakingApi provideApi() {

        return new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(BakingApi.class);
    }
    @Singleton
    @Provides
    AppDatabase provideDb(Application app) {
        return Room.databaseBuilder(app, AppDatabase.class, "bakingapp.db").build();
    }
}
