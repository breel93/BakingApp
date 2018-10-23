package com.example.breezil.bakingapp.api;

import android.arch.lifecycle.LiveData;

import com.example.breezil.bakingapp.model.Recipe;
import com.example.breezil.bakingapp.utils.helpers.ApiResponse;

import java.util.List;

import retrofit2.http.GET;

public interface BakingApi {
//    https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json

    @GET("baking.json")
    LiveData<ApiResponse<List<Recipe>>> getRecipes();
}
