package com.corral.firebase.shailshah.bakingapp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.corral.firebase.shailshah.bakingapp.provider.BakingAppContractor;
import com.corral.firebase.shailshah.bakingapp.utils.OpenBakingJsonUtils;

/**
 * Created by shailshah on 11/4/17.
 */
public class GridWidgetService extends RemoteViewsService
{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewFactory(this.getApplicationContext());
    }
}

 class GridRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    Context mContext;
    Cursor mCursor;
     private Bitmap thumbnail_backgroung1;






     public GridRemoteViewFactory(Context applicationContext)
    {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {
        mCursor = mContext.getContentResolver().query(
                BakingAppContractor.BakeryEntry.CONTENT_URI,
                null,
                null,
                null
                ,null
        );

    }


    @Override
    public void onDataSetChanged() {
        Uri BAKERY_URI  = BakingAppContractor.BakeryEntry.CONTENT_URI;
        if (mCursor !=null) mCursor.close();
        mCursor = mContext.getContentResolver().query(
                BAKERY_URI,
                null,
                null,
                null
                ,null
        );

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mCursor ==null) return 0;
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mCursor == null || mCursor.getCount() ==0) return null;
            mCursor.moveToPosition(position);
        String result = new String();
        RemoteViews views = new RemoteViews(mContext.getPackageName(),R.layout.bakery_widjet_provider);

        String [] ingredient = OpenBakingJsonUtils.convertStringToArray(mCursor.getString(MainActivity.INDEX_WIDGET_INFO));
        for (int i = 0; i< ingredient.length ; i++ )
        {
            result += ingredient[i];
        }

        thumbnail_backgroung1 = OpenBakingJsonUtils.convertByteToBitmap(mCursor.getBlob(MainActivity.INDEX_THUMBNAIL_URL));
        Log.v(GridWidgetService.class.getSimpleName(),"The result is "  + result);

        views.setTextViewText(R.id.name,mCursor.getString(MainActivity.INDEX_BAKERY_NAME));
        views.setTextViewText(R.id.ingredient_widget,result);

        views.setImageViewBitmap(R.id.imageview4,thumbnail_backgroung1);

        String value = String.valueOf(position);
        Intent intent = new Intent(mContext,RacipesListActivity.class);
        intent.putExtra("Values",value);

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext,0,intent,0);

    views.setOnClickPendingIntent(R.id.imageview4,pendingIntent);

        return views;
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
        return false;
    }


}
