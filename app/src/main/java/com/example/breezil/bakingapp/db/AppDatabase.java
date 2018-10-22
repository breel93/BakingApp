package com.example.breezil.bakingapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.breezil.bakingapp.model.Ingredient;
import com.example.breezil.bakingapp.model.Recipe;
import com.example.breezil.bakingapp.model.Step;

@Database(entities = {Recipe.class, Ingredient.class, Step.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    abstract public BakingDao bakingDao();
}