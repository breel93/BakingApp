package com.example.breezil.bakingapp.db;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.example.breezil.bakingapp.model.Ingredient;
import com.example.breezil.bakingapp.model.Recipe;
import com.example.breezil.bakingapp.model.RecipeView;
import com.example.breezil.bakingapp.model.Step;

import java.util.List;

@Dao
public abstract class BakingDao {


    public void saveRecipes(List<Recipe> recipes) {
        for (Recipe recipe : recipes) {

            List<Ingredient> ingredients = recipe.getIngredients();
            for (Ingredient ingredient : ingredients) {
                ingredient.setRecipeId(recipe.getId());
            }
            insertIngredients(ingredients);

            List<Step> steps = recipe.getSteps();
            for (Step step : steps) {
                step.setRecipeId(recipe.getId());
            }
            insertSteps(steps);
        }
        insertRecipes(recipes);
    }

    @Transaction
    @Query("SELECT * FROM Recipe")
    public abstract LiveData<List<RecipeView>> getRecipes();

    @Transaction
    @Query("SELECT * FROM Recipe WHERE id = :id")
    public abstract RecipeView getRecipe(int id);

    @Insert
    public abstract void insertRecipes(List<Recipe> recipes);

    @Insert
    public abstract void insertIngredients(List<Ingredient> ingredients);

    @Insert
    public abstract void insertSteps(List<Step> steps);

}
