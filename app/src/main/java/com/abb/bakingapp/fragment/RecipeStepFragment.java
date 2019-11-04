package com.abb.bakingapp.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.abb.bakingapp.adapter.IngredientsAdapter;
import com.abb.bakingapp.databinding.FragmentRecipeStepBinding;
import com.abb.bakingapp.model.Recipe;
import com.abb.bakingapp.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

/**
 * Created by Abarajithan
 */
public class RecipeStepFragment extends Fragment {

    private static final String EXTRA_STEP_ID = "step_id";
    private static final String EXTRA_RECIPE = "recipe";

    public static RecipeStepFragment create(Recipe recipe, int stepId) {
        RecipeStepFragment fragment = new RecipeStepFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_STEP_ID, stepId);
        args.putParcelable(EXTRA_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    private Recipe recipe;
    private Step step;

    private SimpleExoPlayer exoPlayer;

    private FragmentRecipeStepBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args == null) {
            return;
        }
        int stepId = args.getInt(EXTRA_STEP_ID);
        recipe = args.getParcelable(EXTRA_RECIPE);
        step = recipe.getSteps().get(stepId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecipeStepBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPlayer();
    }

    private void initPlayer() {
        exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext());
        exoPlayer.prepare(buildMediaSource(Uri.parse(step.getVideoURL())));
        exoPlayer.setPlayWhenReady(true);
        binding.recipeStepPlayer.setPlayer(exoPlayer);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getContext(), "exoplayer");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        populateViews();

    }

    private void populateViews() {
        if (binding.recipeStepDesc != null) {
            binding.recipeStepDesc.setText(step.getDescription());
        }
        if (binding.recipeStepIngredients != null) {
            binding.recipeStepIngredients.setHasFixedSize(true);
            binding.recipeStepIngredients.setItemAnimator(new DefaultItemAnimator());
            binding.recipeStepIngredients.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.recipeStepIngredients.setAdapter(new IngredientsAdapter(recipe.getIngredients()));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (exoPlayer != null) {
            exoPlayer.release();
        }
    }
}
