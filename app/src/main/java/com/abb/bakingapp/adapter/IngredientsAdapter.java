package com.abb.bakingapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abb.bakingapp.databinding.ItemIngredientBinding;
import com.abb.bakingapp.model.Ingredient;

import java.util.List;

/**
 * Created by Abarajithan
 */
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientHolder> {

    private List<Ingredient> ingredients;

    public IngredientsAdapter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemIngredientBinding binding = ItemIngredientBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new IngredientHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {
        Ingredient ingredient = ingredients.get(holder.getAdapterPosition());
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        return (this.ingredients == null) ? 0 : this.ingredients.size();
    }

    class IngredientHolder extends RecyclerView.ViewHolder {

        private final ItemIngredientBinding binding;

        IngredientHolder(@NonNull ItemIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Ingredient ingredient) {
            binding.setIngredient(ingredient);
        }

    }

}
