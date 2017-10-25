package com.corral.firebase.shailshah.bakingapp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.corral.firebase.shailshah.bakingapp.data.DbHelper;

/**
 * Created by shailshah on 10/25/17.
 */

public class BakingProvider extends ContentProvider {

    public static final int CODE_BAKERY = 100;
    public static final int CODE_WITH_TITLE = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static DbHelper mOpenHelper;


    public static UriMatcher buildUriMatcher()
    {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BakingAppContractor.CONTENT_AUTHORITY;

        matcher.addURI(authority, BakingAppContractor.PATH_BAKERY,CODE_BAKERY);

        matcher.addURI(authority, BakingAppContractor.PATH_BAKERY + "/#",CODE_WITH_TITLE);

        return matcher;
    }


    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case CODE_BAKERY:
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(BakingAppContractor.BakeryEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;

        switch (sUriMatcher.match(uri))
        {
            case CODE_WITH_TITLE:
            {
                String titleString = uri.getLastPathSegment();

                String[] selestionArgument = new String[]{titleString};

                cursor = mOpenHelper.getReadableDatabase().query(
                        BakingAppContractor.BakeryEntry.TABLE_NAME,projection, BakingAppContractor.BakeryEntry.COLUMN_NAME,
                        selestionArgument,
                        null,null,sortOrder
                );
                break;
            }

            case CODE_BAKERY:
            {
                cursor = mOpenHelper.getReadableDatabase().query(
                       BakingAppContractor.BakeryEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,null,sortOrder

                );
                break;
            }

            default:
            {
                throw new UnsupportedOperationException("Unknown uri "+ uri);
            }


        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case CODE_BAKERY:
                db.beginTransaction();
                int rowsInserted = 0;
                try {

                    long _id = db.insert(BakingAppContractor.BakeryEntry.TABLE_NAME, null, values);
                    if (_id != -1) {

                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                return uri;

        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.delete(BakingAppContractor.BakeryEntry.TABLE_NAME, selection, selectionArgs);
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
    @Override
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
