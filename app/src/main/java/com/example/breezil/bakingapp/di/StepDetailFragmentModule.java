package com.example.breezil.bakingapp.di;

import com.example.breezil.bakingapp.ui.StepDetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class StepDetailFragmentModule {
    @ContributesAndroidInjector
    abstract StepDetailFragment contributeStepDetailFragment();
}

