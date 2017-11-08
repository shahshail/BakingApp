package com.corral.firebase.shailshah.bakingapp.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import static com.corral.firebase.shailshah.bakingapp.provider.BakingAppContractor.BakeryEntry.CONTENT_URI;

/**
 * Created by shailshah on 10/25/17.
 */

public class BakingAppContractor {
    public static final String CONTENT_AUTHORITY = "com.corral.firebase.shailshah.bakingapp.provider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_BAKERY = "baking";


    public static final class BakeryEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "baking";

        public static final String COLUMN_BAKERY_ID = "item_id";

        public static final String COLUMN_NAME = "item_name";

        public static final String COLUMN_QUANTITY = "ingredient_quantity";

        public static final String COLUMN_MEASURE = "ingredient_measure";

        public static final String COLUMN_INGREDIENT = "ingredient_ingredient";

        public static final String COLUMN_STEPS_ID  = "steps_id";

        public static final String COLUMN_SHORT_DESCRIPTION = "steps_short_description";

        public static final String COLUMN_DESCRIPTION = "step_description";

        public static final String COLUMN_VIDEO_URL = "step_video_url";

        public static final String COLUMN_THUMBNAIL_URL = "step_thumbnail_url";

        public static final String COLUMN_THUMBNAIL = "step_thumbnails";

        public static final String COLUMN_SERVINGS = "servings";

        public static final String COLIMN_IMAGE_URL = "images";
        public static final String COLUMN_WIDGET_INFO = "widgetinfo";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_BAKERY)
                .build();


    }
    public static Uri buildWeatherUriWithTitle(String title) {
        return CONTENT_URI.buildUpon()
                .appendPath(title)
                .build();
    }
}
