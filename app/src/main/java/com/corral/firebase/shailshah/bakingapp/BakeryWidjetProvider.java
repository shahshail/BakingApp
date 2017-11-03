package com.corral.firebase.shailshah.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Paint;
import android.util.Log;
import android.widget.RemoteViews;

import com.corral.firebase.shailshah.bakingapp.provider.BakingAppContractor;
import com.corral.firebase.shailshah.bakingapp.utils.OpenBakingJsonUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Implementation of App Widget functionality.
 */
public class BakeryWidjetProvider extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bakery_widjet_provider);

       Intent intent = new Intent(context,RacipesListActivity.class);
        intent.putExtra("Values","1");
        intent.setAction("1");

        ContentResolver resolver = context.getContentResolver();
        Cursor mCursor = resolver.query(BakingAppContractor.BakeryEntry.CONTENT_URI,null,null,null,null);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.thumbnail_1,pendingIntent);

        mCursor.moveToPosition(0);
        views.setImageViewBitmap(R.id.thumbnail_1, scaleDownBitmap(OpenBakingJsonUtils.convertByteToBitmap(mCursor.getBlob(MainActivity.INDEX_THUMBNAIL_URL)),50,context));
        views.setTextViewText(R.id.name_1,mCursor.getString(MainActivity.INDEX_BAKERY_NAME));

        mCursor.moveToPosition(1);
        views.setTextViewText(R.id.name_2,mCursor.getString(MainActivity.INDEX_BAKERY_NAME));
        views.setImageViewBitmap(R.id.thumbnail_2, scaleDownBitmap(OpenBakingJsonUtils.convertByteToBitmap(mCursor.getBlob(MainActivity.INDEX_THUMBNAIL_URL)),50,context));

        mCursor.moveToPosition(2);
        views.setTextViewText(R.id.name_3,mCursor.getString(MainActivity.INDEX_BAKERY_NAME));
        views.setImageViewBitmap(R.id.thumbnail_3, scaleDownBitmap(OpenBakingJsonUtils.convertByteToBitmap(mCursor.getBlob(MainActivity.INDEX_THUMBNAIL_URL)),50,context));

        mCursor.moveToPosition(3);
        views.setTextViewText(R.id.name_4,mCursor.getString(MainActivity.INDEX_BAKERY_NAME));
        views.setImageViewBitmap(R.id.thumbnail_4, scaleDownBitmap(OpenBakingJsonUtils.convertByteToBitmap(mCursor.getBlob(MainActivity.INDEX_THUMBNAIL_URL)),50,context));




        BlurMaskFilter blurFilter = new BlurMaskFilter(5, BlurMaskFilter.Blur.OUTER);
        Paint shadowPaint = new Paint();
        shadowPaint.setMaskFilter(blurFilter);

        int[] offsetXY = new int[2];
       // Bitmap shadowImage = originalBitmap.extractAlpha(shadowPaint, offsetXY);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

    private static Bitmap compressBitmapImage(Bitmap original)
    {


        ByteArrayOutputStream out = new ByteArrayOutputStream();
        original.compress(Bitmap.CompressFormat.PNG, 10, out);
        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));

        Log.e("Original   dimensions", original.getWidth()+" "+original.getHeight());
        Log.e("Compressed dimensions", decoded.getWidth()+" "+decoded.getHeight());
        return original;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

