package com.example.breezil.bakingapp.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.breezil.bakingapp.R;
import com.example.breezil.bakingapp.ui.IngredientFragment;
import com.example.breezil.bakingapp.ui.StepFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    Context context;
    SparseArray<Fragment> fragmentSparseArray = new SparseArray<>();


    private static final int Step_Position = 0;
    private static final int Ingredient_Position = 1;


    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == Step_Position){

            return new StepFragment();
        }else{

            return new IngredientFragment();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Resources resources = context.getResources();
        switch (position){
            case Step_Position:
                return resources.getString(R.string.step);

            case Ingredient_Position:
                return resources.getString(R.string.ingredient);
            default:
                    return "none";

        }
    }
    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container,position);
        fragmentSparseArray.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        fragmentSparseArray.remove(position);
        super.destroyItem(container,position,object);
    }
}
