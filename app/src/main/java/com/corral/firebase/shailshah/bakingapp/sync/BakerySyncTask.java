package com.corral.firebase.shailshah.bakingapp.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.corral.firebase.shailshah.bakingapp.provider.BakingAppContractor;
import com.corral.firebase.shailshah.bakingapp.utils.OpenBakingJsonUtils;

/**
 * Created by shailshah on 10/26/17.
 */

public class BakerySyncTask {
    synchronized public static void syncBakery(Context context)
    {
        try {
            ContentValues[] bakeryDataValues = OpenBakingJsonUtils.getSimpleMovieDataFromJson(context);

            if (bakeryDataValues != null)
            {
                ContentResolver bakeryContentResolver = context.getContentResolver();
                bakeryContentResolver.delete(BakingAppContractor.BakeryEntry.CONTENT_URI, null,null);

               int status = bakeryContentResolver.bulkInsert(BakingAppContractor.BakeryEntry.CONTENT_URI,
                        bakeryDataValues);
                Log.v(BakerySyncTask.class.getSimpleName(),"Total numbers of rows are inserted.. " + status);

            }
            else
            {
                Log.v(BakerySyncTask.class.getSimpleName(),"SORRY THE BAKERY DATA ARE NULL>>>PLEASE TRY AGAIN LATER>>>>>");
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
