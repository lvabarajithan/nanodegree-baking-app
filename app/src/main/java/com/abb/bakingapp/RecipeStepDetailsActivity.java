package com.abb.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.abb.bakingapp.fragment.RecipeStepFragment;
import com.abb.bakingapp.model.Recipe;

/**
 * Created by Abarajithan
 */
public class RecipeStepDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_STEP_ID = "step_id";
    private static final String EXTRA_RECIPE = "recipe";

    public static void start(Context context, Recipe recipe, int stepId) {
        Intent starter = new Intent(context, RecipeStepDetailsActivity.class);
        starter.putExtra(EXTRA_STEP_ID, stepId);
        starter.putExtra(EXTRA_RECIPE, recipe);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_recipe_step_details);

        Recipe recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);

        int stepId = getIntent().getIntExtra(EXTRA_STEP_ID, -1);
        if (stepId == -1) {
            Toast.makeText(this, "Invalid step", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (getResources().getBoolean(R.bool.isLandscape)) {
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().setTitle(getString(R.string.recipe_step_detail_title, stepId, recipe.getName()));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipeStepDetailsContainer, RecipeStepFragment.create(recipe, stepId))
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
