package com.corral.firebase.shailshah.bakingapp.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by shailshah on 10/26/17.
 */

public class BakeryIntentServices extends IntentService{


    public BakeryIntentServices() {
        super("BakeryIntentServices");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        BakerySyncTask.syncBakery(this);

    }
}
