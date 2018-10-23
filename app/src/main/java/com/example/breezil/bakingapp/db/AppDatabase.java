package com.example.breezil.bakingapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.breezil.bakingapp.model.Ingredient;
import com.example.breezil.bakingapp.model.Recipe;
import com.example.breezil.bakingapp.model.Step;


@Database(entities = {Recipe.class, Ingredient.class, Step.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase recipeDatabase;

    abstract public BakingDao bakingDao();
    public static synchronized AppDatabase getInstance(Context context) {
        if(recipeDatabase == null) {
            recipeDatabase = Room.databaseBuilder(context, AppDatabase.class, "bakingapp.db").build();
        }
        return recipeDatabase;
    }

}