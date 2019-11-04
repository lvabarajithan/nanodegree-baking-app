package com.abb.bakingapp.arch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.abb.bakingapp.data.RecipeService;
import com.abb.bakingapp.model.Recipe;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Abarajithan
 */
public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Recipe>> recipesLiveData;
    private List<Recipe> recipes;

    private CompositeDisposable disposable;
    private RecipeService recipeService;

    public MainViewModel() {
        this.recipesLiveData = new MutableLiveData<>();
        this.recipes = null;
        this.disposable = new CompositeDisposable();
        this.recipeService = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(RecipeService.class);
    }

    private void setRecipes(List<Recipe> recipes) {
        this.recipesLiveData.setValue(recipes);
        this.recipes = recipes;
    }

    public LiveData<List<Recipe>> getRecipesLiveData() {
        return recipesLiveData;
    }

    public void loadRecipes() {
        if (this.recipes != null) {
            this.recipesLiveData.setValue(recipes);
            return;
        }
        Disposable recipesDisposable = recipeService.getRecipes().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((recipes, throwable) -> {
                    if (throwable != null) {
                        throwable.printStackTrace();
                    }
                    setRecipes(recipes);
                });
        this.disposable.add(recipesDisposable);
    }

    @Override
    protected void onCleared() {
        this.disposable.dispose();
    }
}
