package com.example.breezil.bakingapp.di;

import com.example.breezil.bakingapp.ui.MainActivity;
import com.example.breezil.bakingapp.ui.StepDetailActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class StepDetailActivityModule {
    @ContributesAndroidInjector(modules = StepDetailFragmentModule.class)
    abstract StepDetailActivity contributeStepDetailActivity();
}


