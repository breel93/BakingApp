package com.example.breezil.bakingapp.ui;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.breezil.bakingapp.ClickCallBack.StepClickListener;
import com.example.breezil.bakingapp.R;
import com.example.breezil.bakingapp.ui.adapter.StepAdapter;
import com.example.breezil.bakingapp.databinding.FragmentStepBinding;
import com.example.breezil.bakingapp.model.Step;
import com.example.breezil.bakingapp.view_model.DetailViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment{

    private static final String RECIPE_NAME = "name";
    private static final String STEP_LIST = "list";
    private static final String STEP = "step";




    String recipeName;

    boolean isTablet;


    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private StepAdapter stepAdapter;
    FragmentStepBinding binding;

    DetailViewModel detailViewModel;



    public StepFragment() {
        // Required empty public constructor
    }



    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_step, container,false);
        binding.stepList.setHasFixedSize(true);
        binding.stepList.setNestedScrollingEnabled(true);
        binding.stepList.addItemDecoration(new DividerItemDecoration(binding.stepList.getContext(),
                LinearLayoutManager.VERTICAL));

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        detailViewModel = ViewModelProviders.of(getActivity(),viewModelFactory)
                .get(DetailViewModel.class);


        stepAdapter = new StepAdapter(detailViewModel.getStepsValue(), stepClickListener());
        binding.stepList.setAdapter(stepAdapter);

        detailViewModel.getSteps().observe(this,steps -> {
            stepAdapter.setList(steps);
        });

        recipeName = detailViewModel.getRecipe().getValue().getName();

    }




    private StepClickListener stepClickListener(){
        return step -> {
            isTablet = getResources().getBoolean(R.bool.is_tablet);
            if(isTablet){
                StepDetailFragment fragment = new StepDetailFragment();
                Bundle args = new Bundle();
                args.putParcelable(STEP, step);
                args.putString(RECIPE_NAME,recipeName);
                args.putParcelableArrayList(STEP_LIST, (ArrayList<Step>) detailViewModel.getStepsValue());
                fragment.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.tab_step_container,fragment).commit();

            }else {
                Intent intent = new Intent(getContext(),StepDetailActivity.class);
                intent.putExtra(STEP,step);
                intent.putParcelableArrayListExtra(STEP_LIST, (ArrayList<Step>) detailViewModel.getStepsValue());
                intent.putExtra(RECIPE_NAME,recipeName);
                startActivity(intent);

            }
        };
    }


}
