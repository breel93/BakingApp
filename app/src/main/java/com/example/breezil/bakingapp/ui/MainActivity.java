package com.example.breezil.bakingapp.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
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

        if(!internetConnected()){
            Toast.makeText(MainActivity.this, "Please ensure your internet is connection and try again..", Toast.LENGTH_LONG).show();
            networkDialog();
        }

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

    private void networkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Connect to Internet")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean internetConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        return networkInfo != null && networkInfo.isConnected();
    }

}
