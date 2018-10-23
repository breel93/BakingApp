package com.example.breezil.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.breezil.bakingapp.R;
import com.example.breezil.bakingapp.db.AppDatabase;
import com.example.breezil.bakingapp.model.Ingredient;
import com.example.breezil.bakingapp.model.RecipeView;
import com.example.breezil.bakingapp.utils.BakingPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class BakingRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {


    private List<String> ingredientList;
    private Context context;
    private AppDatabase appDatabase;


    public BakingRemoteViewsFactory(Context context) {
        this.context = context;
        appDatabase = AppDatabase.getInstance(context);
    }

    @Override
    public void onCreate() {
        ingredientList = new ArrayList<>();
    }

    @Override
    public void onDataSetChanged() {



        int recipeId = BakingPreference.getRecipeId(context);

        if(recipeId != 0 ){
            ingredientList = new ArrayList<>();

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
