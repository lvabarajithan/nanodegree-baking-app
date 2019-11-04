package com.abb.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.abb.bakingapp.R;
import com.abb.bakingapp.adapter.RecipesAdapter;
import com.abb.bakingapp.arch.MainViewModel;
import com.abb.bakingapp.databinding.ConfigureIngredientsWidgetBinding;
import com.abb.bakingapp.model.Recipe;
import com.abb.bakingapp.util.ColumnUtil;

/**
 * Created by Abarajithan
 */
public class IngredientsWidgetConfigure extends AppCompatActivity implements RecipesAdapter.RecipeClickListener {

    private MainViewModel viewModel;
    private RecipesAdapter adapter;

    private ConfigureIngredientsWidgetBinding binding;

    private int appWidgetId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.configure_ingredients_widget);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        initViews();

        subscribeToRecipes();

        loadAllRecipes();

    }

    private void initViews() {
        binding.widgetRecipeList.setItemAnimator(new DefaultItemAnimator());
        binding.widgetRecipeList.setHasFixedSize(true);
        if (getResources().getBoolean(R.bool.isTablet)) {
            binding.widgetRecipeList.setLayoutManager(new GridLayoutManager(this, ColumnUtil.getSpanCount(this)));
        } else {
            binding.widgetRecipeList.setLayoutManager(new LinearLayoutManager(this));
        }

        this.adapter = new RecipesAdapter(this);
        binding.widgetRecipeList.setAdapter(adapter);
    }

    private void loadAllRecipes() {
        viewModel.loadRecipes();
    }

    private void subscribeToRecipes() {
        viewModel.getRecipesLiveData().observe(this, recipes -> {
            adapter.setRecipes(recipes);
        });
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        BakingAppWidgetProvider.updateAppWidget(this, appWidgetManager,
                appWidgetId, recipe);

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}
