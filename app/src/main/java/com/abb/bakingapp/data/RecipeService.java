package com.abb.bakingapp.data;

import com.abb.bakingapp.model.Recipe;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by Abarajithan
 */
public interface RecipeService {

    @GET("baking.json")
    Single<List<Recipe>> getRecipes();

}
