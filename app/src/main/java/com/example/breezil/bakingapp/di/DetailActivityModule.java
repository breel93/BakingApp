package com.example.breezil.bakingapp.di;

import com.example.breezil.bakingapp.ui.DetailActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DetailActivityModule {
    @ContributesAndroidInjector(modules = DetailFragmentsModule.class)
    abstract DetailActivity contributeDetailActivity();
}
