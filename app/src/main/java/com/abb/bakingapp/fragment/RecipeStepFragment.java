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

import com.abb.bakingapp.R;
import com.abb.bakingapp.adapter.IngredientsAdapter;
import com.abb.bakingapp.databinding.FragmentRecipeStepBinding;
import com.abb.bakingapp.model.Recipe;
import com.abb.bakingapp.model.Step;
import com.bumptech.glide.Glide;
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

    private static final String SEEK_POSITION = "seek_position";
    private static final String PLAY_WHEN_READY = "play_when_ready";

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

    private long seekPosition = -1;
    private boolean playWhenReady = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            seekPosition = savedInstanceState.getLong(SEEK_POSITION);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
        }
        Bundle args = getArguments();
        if (args != null) {
            int stepId = args.getInt(EXTRA_STEP_ID);
            recipe = args.getParcelable(EXTRA_RECIPE);
            step = recipe.getSteps().get(stepId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecipeStepBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!step.getVideoURL().isEmpty()) {
            initPlayer();
        } else {
            binding.recipeStepPlayer.setVisibility(View.GONE);
            binding.recipeStepThumbnail.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(step.getThumbnailURL())
                    .error(R.drawable.ic_videocam_off_black_24dp)
                    .into(binding.recipeStepThumbnail);
        }
    }

    private void initPlayer() {
        exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext());
        binding.recipeStepPlayer.setPlayer(exoPlayer);
        exoPlayer.prepare(buildMediaSource(Uri.parse(step.getVideoURL())));
        if (seekPosition != -1) {
            exoPlayer.seekTo(seekPosition);
        }
        exoPlayer.setPlayWhenReady(playWhenReady);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getContext(), "BakingApp");
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
    public void onPause() {
        super.onPause();
        if (exoPlayer != null) {
            seekPosition = exoPlayer.getCurrentPosition();
            playWhenReady = exoPlayer.getPlayWhenReady();

            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(SEEK_POSITION, seekPosition);
        outState.putBoolean(PLAY_WHEN_READY, playWhenReady);
    }
}
