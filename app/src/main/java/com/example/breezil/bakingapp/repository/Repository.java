package com.example.breezil.bakingapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.breezil.bakingapp.api.BakingApi;
import com.example.breezil.bakingapp.model.Recipe;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class Repository {

    private final BakingApi bakingApi;
    private MutableLiveData<List<Recipe>> recipes;

    @Inject
    Repository(BakingApi bakingApi) {
        this.bakingApi = bakingApi;
    }

    public MutableLiveData<List<Recipe>> getRecipes() {
        if(recipes == null){
            recipes = new MutableLiveData<>();
            bakingApi.getRecipes().enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    recipes.setValue(response.body());
                    Log.d("Success", response.message());
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    // TODO Failure
                    Log.d("Fail", t.getMessage());
                }
            });

        }

        return recipes;
    }
}
