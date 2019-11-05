package com.abb.bakingapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.abb.bakingapp.RecipeStepsActivity;
import com.abb.bakingapp.adapter.StepsAdapter;
import com.abb.bakingapp.databinding.FragmentRecipeStepListBinding;
import com.abb.bakingapp.model.Recipe;

/**
 * Created by Abarajithan
 */
public class RecipeStepListFragment extends Fragment {

    private static final String EXTRA_RECIPE = "recipe";

    public static RecipeStepListFragment create(Recipe recipe) {
        RecipeStepListFragment fragment = new RecipeStepListFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    private int selectItem = -1;

    private FragmentRecipeStepListBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecipeStepListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Recipe recipe = getArguments().getParcelable(EXTRA_RECIPE);

        binding.recipeStepList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recipeStepList.setItemAnimator(new DefaultItemAnimator());
        binding.recipeStepList.setHasFixedSize(false);

        binding.recipeStepList.setAdapter(new StepsAdapter(recipe.getSteps(), selectItem,
                position -> ((RecipeStepsActivity) getActivity()).showRecipeStepDetails(position)));

    }

    public void selectItem(int pos) {
        this.selectItem = pos;
    }

}
