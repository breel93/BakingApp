package com.example.breezil.bakingapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.breezil.bakingapp.databinding.RecipeItemBinding;
import com.example.breezil.bakingapp.model.Recipe;
import com.example.breezil.bakingapp.ClickCallBack.RecipeClickListener;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private List<Recipe> recipeList;
    private RecipeClickListener clickListener;




    public RecipeAdapter(RecipeClickListener clickListener) {
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecipeItemBinding binding = RecipeItemBinding.inflate(layoutInflater, parent, false);
        return new RecipeHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.bind(recipe,clickListener);
    }

    @Override
    public int getItemCount() {
        if (recipeList == null) {
            return 0;
        }
        return recipeList.size();
    }

    public void setList(List<Recipe> list) {
        recipeList = list;
        notifyDataSetChanged();
    }


    class RecipeHolder extends RecyclerView.ViewHolder{

        RecipeItemBinding binding;
        public RecipeHolder(RecipeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
       public void bind(Recipe recipe, RecipeClickListener clickCallback){

            itemView.setOnClickListener(v -> clickCallback.showDetails(recipe));
            binding.recipeName.setText(recipe.getName());
            binding.recipeServing.setText(String.valueOf(recipe.getServings()) + " servings");


       }
    }


}
