package com.abb.bakingapp.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Abarajithan
 */
public class ColumnUtil {

    public static int getSpanCount(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float dpWidth = metrics.widthPixels / metrics.density;
        int scalingFactor = 200;
        int columns = (int) dpWidth / scalingFactor;
        if (columns < 2) return 2;
        return columns;
    }

}
