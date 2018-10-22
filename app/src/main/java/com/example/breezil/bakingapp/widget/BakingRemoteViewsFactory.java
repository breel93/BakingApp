package com.example.breezil.bakingapp.widget;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.example.breezil.bakingapp.R;
import com.example.breezil.bakingapp.db.AppDatabase;
import com.example.breezil.bakingapp.model.Ingredient;
import com.example.breezil.bakingapp.model.RecipeView;
import com.example.breezil.bakingapp.ui.MainActivity;
import com.example.breezil.bakingapp.utils.BakingPreference;
import com.example.breezil.bakingapp.view_model.DetailViewModel;
import com.example.breezil.bakingapp.view_model.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import static android.support.constraint.Constraints.TAG;

public class BakingRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private MainViewModel viewModel;

    private List<String> ingredientList;
    private Context context;
    private AppDatabase appDatabase;

    public BakingRemoteViewsFactory(Context context) {
        this.context = context;
        appDatabase =  Room.databaseBuilder(context, AppDatabase.class, "bakingapp.db").build();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {



        int recipeId = BakingPreference.getRecipeId(context);
        Log.d(TAG, "onDataSetChanged: " + recipeId);

        if(recipeId != 0 ){
            ingredientList = new ArrayList<>();

//            viewModel = ViewModelProviders.of((FragmentActivity) context,viewModelFactory)
//                    .get(MainViewModel.class);
//            viewModel.getRecipes().observe((LifecycleOwner) context, recipes -> {
//                if(recipes != null){
//                    for(Ingredient ingredient : recipes.get(recipeId).getIngredients())
//                    {
//                        ingredientList.add(String.format(Locale.getDefault(), "%.1f %s %s\n",
//                                ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredient()));
//
//                    }
//
//                }
//            });
            RecipeView recipeView =
                    appDatabase.bakingDao().getRecipe(recipeId);



            if (recipeView != null) {
                for (Ingredient ingredient : recipeView.ingredients) {
                    ingredientList.add(String.format(Locale.getDefault(), "%.1f %s %s",
                            ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredient()));
                }
            }
        }

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
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        Intent intent = new Intent();
        remoteViews.setTextViewText(R.id.widget_item_textview, ingredientList.get(position));
        remoteViews.setOnClickFillInIntent(R.id.widget_item_textview, intent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
