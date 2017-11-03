package com.corral.firebase.shailshah.bakingapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.corral.firebase.shailshah.bakingapp.provider.BakingAppContractor;

/**
 * Created by shailshah on 9/27/17.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bakerydb.db";
    private static final int DATABASE_VERSION = 8;
    public DbHelper(Context context)
    {
         super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

         final String SQL_BAHERY_QUERY = "CREATE TABLE " + BakingAppContractor.BakeryEntry.TABLE_NAME +
                " (" + BakingAppContractor.BakeryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 BakingAppContractor.BakeryEntry.COLUMN_BAKERY_ID + " INTEGER, " +
                 BakingAppContractor.BakeryEntry.COLUMN_NAME + " REAL NOT NULL, " +
                 BakingAppContractor.BakeryEntry.COLUMN_QUANTITY + " REAL, " +
                 BakingAppContractor.BakeryEntry.COLUMN_MEASURE + " REAL, " +
                 BakingAppContractor.BakeryEntry.COLUMN_INGREDIENT + " REAL, " +
                 BakingAppContractor.BakeryEntry.COLUMN_STEPS_ID+ " REAL, " +
                 BakingAppContractor.BakeryEntry.COLUMN_SHORT_DESCRIPTION + " REAL, "+
                 BakingAppContractor.BakeryEntry.COLUMN_DESCRIPTION + " REAL, " +
                 BakingAppContractor.BakeryEntry.COLUMN_VIDEO_URL + " REAL, " +
                 BakingAppContractor.BakeryEntry.COLUMN_THUMBNAIL_URL + " BLOB, " +
                 BakingAppContractor.BakeryEntry.COLUMN_SERVINGS + " REAL, " +
                 BakingAppContractor.BakeryEntry.COLIMN_IMAGE_URL + " REAL" +
                 ");";


        sqLiteDatabase.execSQL(SQL_BAHERY_QUERY);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BakingAppContractor.BakeryEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
