package com.example.breezil.bakingapp.ui;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.breezil.bakingapp.R;
import com.example.breezil.bakingapp.model.Step;
import com.example.breezil.bakingapp.view_model.DetailViewModel;


import java.util.ArrayList;


import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class StepDetailActivity extends AppCompatActivity  implements
        HasSupportFragmentInjector{

    private static final String RECIPE_NAME = "name";
    private static final String STEP = "step";
    private static final String STEP_LIST = "list";

    Step step;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    DetailViewModel detailViewModel;

    ArrayList<Step> stepList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);



        detailViewModel = ViewModelProviders.of(this,viewModelFactory).get(DetailViewModel.class);


        detailViewModel.setStep(getStep());

        if (!getIntent().hasExtra(STEP)) {
            finish();
            return;
        }


        String recipeName  = getIntent().getStringExtra(RECIPE_NAME);

        step = getIntent().getParcelableExtra(STEP);
        stepList = getIntent().getParcelableArrayListExtra(STEP_LIST);



        if(savedInstanceState == null){

            StepDetailFragment stepDetailFragment = StepDetailFragment.theStep(stepList);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.step_detail_container, stepDetailFragment)
                    .commit();

        }



        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(recipeName);

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Nullable
    private Step getStep() {
        final Intent intent = this.getIntent();
        if (intent.hasExtra(STEP)) {
            return intent.getParcelableExtra(STEP);
        } else {
            return null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}
