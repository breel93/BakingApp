package com.example.breezil.bakingapp.widget;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.breezil.bakingapp.model.Ingredient;
import com.example.breezil.bakingapp.utils.BakingPreference;
import com.example.breezil.bakingapp.view_model.DetailViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class BakingRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private DetailViewModel viewModel;

    private List<String> ingredientList;
    private Context context;

    public BakingRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {



        int recipeId = BakingPreference.getRecipeId(context);
        if(recipeId != -1 ){
            ingredientList = new ArrayList<>();
        }

//        viewModel = ViewModelProviders.of((FragmentActivity) context,viewModelFactory)
//                .get(DetailViewModel.class);
//        viewModel.setRecipeId(recipeId);
//
//        viewModel.getRecipeList().observe((LifecycleOwner) context, recipes -> {
//            if(recipes != null ){
//                for(Ingredient ingredient : recipes.get(recipeId).getIngredients())
//                {
//                    ingredientList.add(String.format(Locale.getDefault(), "%.1f %s %s\n",
//                            ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredient()));
//                }
//            }
//        });

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientList == null ? 0 : ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
