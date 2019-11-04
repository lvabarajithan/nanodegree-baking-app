package com.abb.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.abb.bakingapp.R;
import com.abb.bakingapp.model.Ingredient;
import com.abb.bakingapp.model.Recipe;

import java.util.List;

import static com.abb.bakingapp.RecipeStepsActivity.EXTRA_RECIPE;

/**
 * Created by Abarajithan
 */
public class IngredientsWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Bundle extra = intent.getBundleExtra(EXTRA_RECIPE);
        Recipe recipe = extra.getParcelable(EXTRA_RECIPE);
        return new IngredientWidgetFactory(this, recipe.getIngredients());
    }

    private class IngredientWidgetFactory implements RemoteViewsService.RemoteViewsFactory {

        private List<Ingredient> ingredients;
        private Context context;

        IngredientWidgetFactory(Context context, List<Ingredient> ingredients) {
            this.ingredients = ingredients;
            this.context = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredients.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews row = new RemoteViews(context.getPackageName(),
                    R.layout.item_widget_ingredient);

            row.setTextViewText(R.id.itemWidgetText, ingredients.get(position).toString());
            return row;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
