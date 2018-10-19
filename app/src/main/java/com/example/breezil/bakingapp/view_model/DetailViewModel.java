package com.example.breezil.bakingapp.view_model;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.breezil.bakingapp.model.Ingredient;
import com.example.breezil.bakingapp.model.Recipe;
import com.example.breezil.bakingapp.model.Step;
import com.example.breezil.bakingapp.repository.Repository;

import java.util.List;

import javax.inject.Inject;

public class DetailViewModel extends ViewModel {

    private final Repository repository;
//    private LiveData<List<Recipe>> recipeList;
//    private int recipeId;

    private MutableLiveData<Recipe> recipe = new MutableLiveData<>();
    private MutableLiveData<Step> step = new MutableLiveData<>();
    private MutableLiveData<List<Step>> steps = new MutableLiveData<>();
    private MutableLiveData<List<Ingredient>> ingredients = new MutableLiveData<>();

    @Inject
    DetailViewModel(Repository repository) {
        this.repository = repository;
    }





    public void setRecipe(Recipe recipe) {
        this.recipe.setValue(recipe);
        this.steps.setValue(recipe.getSteps());
        this.ingredients.setValue(recipe.getIngredients());
    }

    public void setStep(Step step){
        this.step.setValue(step);
    }

    public MutableLiveData<Step> getStep() {
        return step;
    }

    public MutableLiveData<Recipe> getRecipe() {
        return recipe;
    }

    public MutableLiveData<List<Step>> getSteps() {
        return steps;
    }

    public MutableLiveData<List<Ingredient>> getIngredients() {
        return ingredients;
    }

    public Recipe getRecipeValue() {
        return recipe.getValue();
    }

    public List<Step> getStepsValue() {
        return steps.getValue();
    }

    public List<Ingredient> getIngredientsValue() {
        return ingredients.getValue();
    }



    public void setStepsValue(List<Step> steps) {
        this.steps.setValue(steps);
    }


//    public int totalSteps(int id){
//        return getRecipeList().getValue().get(id).getSteps().size();
//    }
//
//
//
//
//    public LiveData<List<Recipe>> getRecipeList(){
//        if(recipeList == null){
//            recipeList = repository.getRecipes();
//        }
//       return recipeList;
//    }



}
