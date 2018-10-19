package com.example.breezil.bakingapp.di;

import com.example.breezil.bakingapp.ui.IngredientFragment;
import com.example.breezil.bakingapp.ui.StepDetailFragment;
import com.example.breezil.bakingapp.ui.StepFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class StepDetailFragmentModule {
    @ContributesAndroidInjector
    abstract StepDetailFragment contributeStepDetailFragment();
}

