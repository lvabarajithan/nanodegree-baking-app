package com.abb.bakingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.test.espresso.IdlingResource;

import com.abb.bakingapp.adapter.RecipesAdapter;
import com.abb.bakingapp.arch.MainViewModel;
import com.abb.bakingapp.databinding.ActivityMainBinding;
import com.abb.bakingapp.model.Recipe;
import com.abb.bakingapp.util.ColumnUtil;
import com.abb.bakingapp.util.ListIdelingResource;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipeClickListener {

    private MainViewModel viewModel;
    private RecipesAdapter adapter;

    private ActivityMainBinding binding;

    private ListIdelingResource idelingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        initViews();

        subscribeToRecipes();

        loadAllRecipes();
    }

    private void initViews() {
        binding.mainRecipeList.setItemAnimator(new DefaultItemAnimator());
        binding.mainRecipeList.setHasFixedSize(true);
        if (getResources().getBoolean(R.bool.isTablet)) {
            binding.mainRecipeList.setLayoutManager(new GridLayoutManager(this, ColumnUtil.getSpanCount(this)));
        } else {
            binding.mainRecipeList.setLayoutManager(new LinearLayoutManager(this));
        }

        this.adapter = new RecipesAdapter(this);
        binding.mainRecipeList.setAdapter(adapter);
    }

    private void loadAllRecipes() {
        viewModel.loadRecipes();
        if (idelingResource != null) {
            idelingResource.setIdleState(false);
        }
    }

    private void subscribeToRecipes() {
        viewModel.getRecipesLiveData().observe(this, recipes -> {
            adapter.setRecipes(recipes);
            if (idelingResource != null) {
                idelingResource.setIdleState(true);
            }
        });
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        RecipeStepsActivity.start(this, recipe);
    }

    /**
     * Only called from test, creates and returns a new {@link ListIdelingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (idelingResource == null) {
            idelingResource = new ListIdelingResource();
        }
        return idelingResource;
    }
}
