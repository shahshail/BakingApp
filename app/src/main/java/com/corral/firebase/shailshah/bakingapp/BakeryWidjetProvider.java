package com.corral.firebase.shailshah.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

private static Cursor mCursor;
    private static Bitmap thumbnail_backgroung1;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews remoteview  = new RemoteViews(context.getPackageName(),R.layout.bakery_widjet_provider);
        //RemoteViews rv;
        //rv = getBakingAppRemoteView(context);
        mCursor = context.getContentResolver().query(
                BakingAppContractor.BakeryEntry.CONTENT_URI,
                null,
                null,
                null
                ,null
        );

        SharedPreferences prefs = context.getSharedPreferences("widget_pref",0);
        int position = prefs.getInt("widget_pref",0);



        Log.v(BakeryWidjetProvider.class.getSimpleName(),"The value of widget preference is "+ position);

        mCursor.moveToPosition(position);

        String result = new String();

        String [] ingredient = OpenBakingJsonUtils.convertStringToArray(mCursor.getString(MainActivity.INDEX_WIDGET_INFO));
        for (int i = 0; i< ingredient.length ; i++ )
        {
            result += ingredient[i];
        }

        thumbnail_backgroung1 = OpenBakingJsonUtils.convertByteToBitmap(mCursor.getBlob(MainActivity.INDEX_THUMBNAIL_URL));
        Log.v(GridWidgetService.class.getSimpleName(),"The result is "  + result);

        remoteview.setTextViewText(R.id.name,mCursor.getString(MainActivity.INDEX_BAKERY_NAME));
        remoteview.setTextViewText(R.id.ingredient_widget,result);
        remoteview.setTextViewText(R.id.widget_titlebar,mCursor.getString(MainActivity.INDEX_BAKERY_NAME)+ " Ingredients");
        remoteview.setImageViewBitmap(R.id.imageview4,thumbnail_backgroung1);

        String value = String.valueOf(position);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteview);
    }

/**
    private static RemoteViews getBakingAppRemoteView(Context context)
    {

        RemoteViews remoteview  = new RemoteViews(context.getPackageName(),R.layout.widget_listview_layout);
        Intent intent = new Intent(context, GridWidgetService.class);
        remoteview.setRemoteAdapter(R.id.widget_list_view, intent);
        Intent appIntent = new Intent(context,MainActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context,0,appIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteview.setPendingIntentTemplate(R.id.widget_list_view,appPendingIntent);
        remoteview.setEmptyView(R.id.widget_list_view,R.id.empty_view);
        return remoteview;
    }
 */

    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

    public static Bitmap compressBitmapImage(Bitmap original)
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

