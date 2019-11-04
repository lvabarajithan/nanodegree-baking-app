package com.abb.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.abb.bakingapp.R;
import com.abb.bakingapp.RecipeStepsActivity;
import com.abb.bakingapp.model.Recipe;

import static com.abb.bakingapp.RecipeStepsActivity.EXTRA_RECIPE;

/**
 * Created by Abarajithan
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Recipe recipe) {
        Intent intent = new Intent(context, RecipeStepsActivity.class);
        intent.putExtra(EXTRA_RECIPE, recipe);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_ingredients);
        views.setOnClickPendingIntent(R.id.widgetRoot, pendingIntent);
        views.setTextViewText(R.id.widgetIngredientsRecipeName, recipe.getName());

        Intent ingredientsServiceIntent = new Intent(context, IngredientsWidgetService.class);
        Bundle extras = new Bundle();
        extras.putParcelable(EXTRA_RECIPE, recipe);
        ingredientsServiceIntent.putExtra(EXTRA_RECIPE, extras);
        ingredientsServiceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        ingredientsServiceIntent.setData(Uri.parse(ingredientsServiceIntent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.widgetIngredientsList, ingredientsServiceIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
