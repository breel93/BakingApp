package com.example.breezil.bakingapp.ui;


import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.breezil.bakingapp.R;
import com.example.breezil.bakingapp.ui.adapter.IngredientAdapter;
import com.example.breezil.bakingapp.databinding.FragmentIngredientBinding;
import com.example.breezil.bakingapp.model.Recipe;
import com.example.breezil.bakingapp.utils.BakingPreference;
import com.example.breezil.bakingapp.view_model.DetailViewModel;
import com.example.breezil.bakingapp.widget.BakingAppWidget;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientFragment extends Fragment {


    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private IngredientAdapter ingredientAdapter;
    FragmentIngredientBinding binding;

    DetailViewModel detailViewModel;
    int recipeId;

    private Recipe recipe;

    public IngredientFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredient, container,false);



        binding.ingredientList.setHasFixedSize(true);
        binding.ingredientList.setNestedScrollingEnabled(false);
        binding.ingredientList.addItemDecoration(new DividerItemDecoration(binding.ingredientList.getContext(),
                LinearLayoutManager.VERTICAL));


        binding.addToWidget.setOnClickListener(v -> {
            addWidget();
        });

        return binding.getRoot();

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ingredientAdapter = new IngredientAdapter();
        binding.ingredientList.setAdapter(ingredientAdapter);

        detailViewModel = ViewModelProviders.of(getActivity(),viewModelFactory)
                .get(DetailViewModel.class);

        detailViewModel.getIngredients().observe(this, ingredients -> {
            if(ingredients != null){
                ingredientAdapter.setList(ingredients);

            }
        });

       recipeId = detailViewModel.getRecipe().getValue().getId();
       recipe = detailViewModel.getRecipe().getValue();

    }


    private void addWidget() {

        BakingPreference.setTitle(getContext(), recipe.getName());
        BakingPreference.setRecipeId(getContext(), recipeId);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(getContext(), BakingAppWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_ingredients_list);

        BakingAppWidget.updateAppWidget(getContext(), appWidgetManager, appWidgetIds);

    }


}
