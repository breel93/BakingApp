package com.example.breezil.bakingapp.view_model;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.breezil.bakingapp.model.Recipe;
import com.example.breezil.bakingapp.repository.Repository;

import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<Recipe>> recipes;
    private Repository repository;

    private int recipeId;



    @Inject
    public MainViewModel(Repository repository) {
        this.repository = repository;
    }

    public  MutableLiveData<List<Recipe>> getRecipes() {
        if (recipes == null) {
            recipes = repository.getRecipes();
        }

        return recipes;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
