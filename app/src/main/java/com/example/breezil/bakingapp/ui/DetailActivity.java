package com.example.breezil.bakingapp.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.example.breezil.bakingapp.R;
import com.example.breezil.bakingapp.adapter.PagerAdapter;
import com.example.breezil.bakingapp.databinding.ActivityDetailBinding;
import com.example.breezil.bakingapp.model.Recipe;
import com.example.breezil.bakingapp.view_model.DetailViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.android.support.HasSupportFragmentInjector;

public class DetailActivity extends DaggerAppCompatActivity implements
        HasSupportFragmentInjector{


    public static final String RECIPE = "recipe";

    @Inject
    ViewModelProvider.Factory viewModelFactory;


    ActivityDetailBinding binding;
    PagerAdapter pagerAdapter;

    DetailViewModel detailViewModel;



    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail);

        detailViewModel = ViewModelProviders.of(this,viewModelFactory).get(DetailViewModel.class);
        detailViewModel.setRecipe(getRecipe());
        detailViewModel.getRecipe().observe(this,recipe -> {
            binding.collapsingToolbar.setTitle(recipe.getName());
        });

        if (!getIntent().hasExtra(RECIPE)) {
            finish();
            return;
        }


        setupToolbar();
        setupAdapter();

    }


    void setupToolbar(){
        setSupportActionBar(binding.dtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    void setupAdapter(){
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),this);
        binding.detailViewpager.setAdapter(pagerAdapter);
        binding.detailTab.setupWithViewPager(binding.detailViewpager);
    }



    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Nullable
    private Recipe getRecipe() {
        final Intent intent = this.getIntent();
        if (intent.hasExtra(RECIPE)) {
            return intent.getParcelableExtra(RECIPE);
        } else {
            return null;
        }
    }
}
