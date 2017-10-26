package com.corral.firebase.shailshah.bakingapp.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by shailshah on 10/26/17.
 */

public class BakeryFirebaseJobservices extends JobService {
    private AsyncTask<Void,Void,Void> mFetchBakeryDataTask;
    @Override
    public boolean onStartJob(JobParameters job) {
        mFetchBakeryDataTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                Context context = getApplicationContext();
                df
                return null;
            }
        }.execute();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
