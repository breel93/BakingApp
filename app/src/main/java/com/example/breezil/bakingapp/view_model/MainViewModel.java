package com.example.breezil.bakingapp.view_model;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.breezil.bakingapp.model.Recipe;
import com.example.breezil.bakingapp.repository.Repository;
import com.example.breezil.bakingapp.utils.helpers.Resource;

import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {
    private LiveData<Resource<List<Recipe>>> recipes;
    private Repository repository;


    @Inject
    public MainViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<List<Recipe>>> getRecipes() {
        if (recipes == null) {
            recipes = repository.getRecipes();
        }

        return recipes;
    }

}
