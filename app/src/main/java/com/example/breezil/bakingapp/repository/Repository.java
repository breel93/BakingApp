package com.example.breezil.bakingapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.breezil.bakingapp.api.BakingApi;
import com.example.breezil.bakingapp.db.AppDatabase;
import com.example.breezil.bakingapp.model.Recipe;
import com.example.breezil.bakingapp.model.RecipeView;
import com.example.breezil.bakingapp.utils.helpers.ApiResponse;
import com.example.breezil.bakingapp.utils.helpers.AppExecutors;
import com.example.breezil.bakingapp.utils.helpers.NetworkBoundResource;
import com.example.breezil.bakingapp.utils.helpers.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Repository {

    private final BakingApi bakingApi;
    private MediatorLiveData<List<Recipe>> recipesList;

    AppExecutors appExecutors;
    AppDatabase database;

    @Inject
    Repository(BakingApi bakingApi, AppExecutors appExecutors, AppDatabase database) {
        this.bakingApi = bakingApi;
        this.appExecutors = appExecutors;
        this.database = database;
    }

    public LiveData<Resource<List<Recipe>>> getRecipes(){
        return new NetworkBoundResource<List<Recipe>, List<Recipe>>(appExecutors){

            @Override
            protected void saveCallResult(@NonNull List<Recipe> item) {
                database.bakingDao().saveRecipes(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Recipe> data) {
                return data == null || data.size() == 0 ;
            }

            @NonNull
            @Override
            protected LiveData<List<Recipe>> loadFromDb() {
                LiveData<List<RecipeView>> recipeViewsLiveData = database.bakingDao().getRecipes();
                recipesList = new MediatorLiveData<>();
                recipesList.addSource(recipeViewsLiveData, recipeViews -> {
                    List<Recipe> recipes = new ArrayList<>();

                    for (RecipeView recipeView : recipeViews) {
                        recipeView.recipe.setIngredients(recipeView.ingredients);
                        recipeView.recipe.setSteps(recipeView.steps);
                        recipes.add(recipeView.recipe);
                    }

                    recipesList.setValue(recipes);
                });
                return recipesList;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Recipe>>> createCall() {
                return bakingApi.getRecipes();
            }
        }.asLiveData();
    }

}
