package com.example.breezil.bakingapp.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.breezil.bakingapp.ClickCallBack.RecipeClickListener;
import com.example.breezil.bakingapp.R;
import com.example.breezil.bakingapp.adapter.RecipeAdapter;
import com.example.breezil.bakingapp.databinding.ActivityMainBinding;
import com.example.breezil.bakingapp.view_model.MainViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {
    public static final String RECIPE = "recipe";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private RecipeAdapter recipeAdapter;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.recipesRecyclerView.setHasFixedSize(true);

        setupViewModel();
        setUpAdapter();

    }

    private void setUpAdapter(){
        RecipeClickListener recipeClickListener = recipe -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(RECIPE, recipe);
            startActivity(intent);
        };
        recipeAdapter = new RecipeAdapter(recipeClickListener);
        binding.recipesRecyclerView.setAdapter(recipeAdapter);
    }
    private void setupViewModel(){
        MainViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        viewModel.getRecipes().observe(this, recipes -> recipeAdapter.setList(recipes));
    }
}
