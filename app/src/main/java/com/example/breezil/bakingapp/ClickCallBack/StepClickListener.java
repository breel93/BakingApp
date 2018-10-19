package com.example.breezil.bakingapp.ClickCallBack;

import android.support.annotation.NonNull;

import com.example.breezil.bakingapp.model.Step;

public interface StepClickListener {
    void showDetails(@NonNull Step step);
}
