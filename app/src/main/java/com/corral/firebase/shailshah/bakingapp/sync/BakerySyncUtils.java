package com.corral.firebase.shailshah.bakingapp.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.corral.firebase.shailshah.bakingapp.provider.BakingAppContractor;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

/**
 * Created by shailshah on 10/26/17.
 */

public class BakerySyncUtils {

    private static final int SYNC_INTERVALHOURS = 15;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVALHOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS/3;

    private static String BAKERY_SYNC_TAG = "bakery-sync";

    private static boolean sInitialized;

    static void BakeryJobDispatcherSync(final Context context)
    {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job syncBakeryJob = dispatcher.newJobBuilder()
                .setService(BakeryFirebaseJobservices.class)
                .setTag(BAKERY_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(SYNC_INTERVAL_SECONDS,SYNC_FLEXTIME_SECONDS+SYNC_INTERVAL_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(syncBakeryJob);

    }

    synchronized public static void initialize(@NonNull final Context context)
    {
        if (sInitialized)return;
        sInitialized =true;

        BakeryJobDispatcherSync(context);
        new AsyncTask<Void,Void,Void>()
        {

            @Override
            protected Void doInBackground(Void... params) {
                Uri bakeryUri = BakingAppContractor.BakeryEntry.CONTENT_URI;

                String[] prjectionColumn = {BakingAppContractor.BakeryEntry._ID};

                Cursor cursor = context.getContentResolver().query(
                        bakeryUri,
                        prjectionColumn,
                        null,
                        null,
                        null);

                if (cursor ==null || cursor.getCount() == 0)
                {
                    startImmediateSync(context);
                }
                cursor.close();

                return null;
            }
        }.execute();
    }

    private static void startImmediateSync(Context context) {
        Intent intentSyncImmediately = new Intent(context, BakeryIntentServices.class);
        context.startService(intentSyncImmediately);
    }
}
