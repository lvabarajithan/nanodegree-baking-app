package com.abb.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.abb.bakingapp.databinding.ActivityRecipeStepsBinding;
import com.abb.bakingapp.fragment.RecipeStepFragment;
import com.abb.bakingapp.fragment.RecipeStepListFragment;
import com.abb.bakingapp.model.Recipe;

/**
 * Created by Abarajithan
 */
public class RecipeStepsActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE = "recipe";

    public static void start(Context context, Recipe recipe) {
        Intent starter = new Intent(context, RecipeStepsActivity.class);
        starter.putExtra(EXTRA_RECIPE, recipe);
        context.startActivity(starter);
    }

    private int prevPos = -1;

    private ActivityRecipeStepsBinding binding;

    private Recipe recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_steps);

        recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);
        if (recipe == null) {
            Toast.makeText(this, "Invalid recipe", Toast.LENGTH_SHORT).show();
            return;
        }

        getSupportActionBar().setTitle(getString(R.string.recipe_steps_title, recipe.getName()));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecipeStepListFragment stepListFragment = RecipeStepListFragment.create(recipe);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipeStepListContainer, stepListFragment)
                .commit();

        if (binding.fragmentRecipeStepsContainer != null) {
            stepListFragment.selectFirstItem();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showRecipeStepDetails(int position) {
        if (this.prevPos == position) {
            return;
        }
        RecipeStepFragment stepFragment = RecipeStepFragment.create(recipe, position);
        if (binding.fragmentRecipeStepsContainer != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentRecipeStepsContainer, stepFragment)
                    .commit();
        } else {
            RecipeStepDetailsActivity.start(this, recipe, position);
        }
        this.prevPos = position;
    }

    public Recipe getRecipe() {
        return this.recipe;
    }
}
