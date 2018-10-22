package com.example.breezil.bakingapp.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.breezil.bakingapp.R;
import com.example.breezil.bakingapp.databinding.IngredientItemBinding;
import com.example.breezil.bakingapp.model.Ingredient;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder> {

    private List<Ingredient> ingredientList;

    public IngredientAdapter() {

    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        IngredientItemBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.ingredient_item, parent,false);
        return new IngredientHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        if(ingredientList == null){
            return 0;
        }
        return ingredientList.size();
    }

    public void setList(List<Ingredient> ingredients){

        ingredientList =  ingredients;
        notifyDataSetChanged();
    }

    class IngredientHolder extends RecyclerView.ViewHolder{
        IngredientItemBinding ingredientItemBinding;


        public IngredientHolder(IngredientItemBinding ingredientItemBinding) {
            super(ingredientItemBinding.getRoot());
            this.ingredientItemBinding = ingredientItemBinding;
        }
        void bind(@NonNull Ingredient ingredient){
            final String amount = ingredient.getQuantity() + " " + ingredient.getMeasure();
            ingredientItemBinding.ingredientName.setText(ingredient.getIngredient());
            ingredientItemBinding.ingredientAmount.setText(amount);
        }
    }
}
