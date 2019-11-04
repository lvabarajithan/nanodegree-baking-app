package com.abb.bakingapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abb.bakingapp.R;
import com.abb.bakingapp.databinding.ItemRecipeListBinding;
import com.abb.bakingapp.model.Recipe;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abarajithan
 */
public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeHolder> {

    private List<Recipe> recipes;
    private RecipeClickListener listener;

    public RecipesAdapter(RecipeClickListener listener) {
        this.recipes = new ArrayList<>();
        this.listener = listener;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecipeListBinding binding = ItemRecipeListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RecipeHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        Recipe recipe = this.recipes.get(position);
        holder.bind(recipe, listener);
    }

    @Override
    public int getItemCount() {
        return (recipes == null) ? 0 : recipes.size();
    }

    class RecipeHolder extends RecyclerView.ViewHolder {

        private final ItemRecipeListBinding binding;

        RecipeHolder(@NonNull ItemRecipeListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Recipe recipe, RecipeClickListener listener) {
            binding.setRecipe(recipe);
            Glide.with(binding.getRoot().getContext())
                    .load(recipe.getImage())
                    .placeholder(R.drawable.recipe_placeholder)
                    .into(binding.recipeImage);
            binding.setListener(listener);
        }
    }

    public interface RecipeClickListener {
        public void onRecipeClick(Recipe recipe);
    }

}
