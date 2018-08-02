package com.example.acer.moviemonsters;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class ContentProviders extends ContentProvider {
    public static final int TASK = 100;
    public static final String AUTHORITY = "com.example.acer.moviemonsters";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH = "Fav";
    public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();
    DataBase dataBase;
    UriMatcher myUri = matcherUri();
    SQLiteDatabase db;
    private DataBase mDataBase;

    public ContentProviders() {
    }

    public static UriMatcher matcherUri() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, PATH, TASK);
        return uriMatcher;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Uri returnUri = null;
        int mt = myUri.match(uri);
        db = mDataBase.getWritableDatabase();
        if (mt == TASK) {
            long res = db.delete("Fav", "id=" + Integer.parseInt(selection), null);
        } else {
            // Implement this to handle requests to delete one or more rows.
            //           throw new UnsupportedOperationException("Not yet implemented");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return mt;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i("In", "insert");
        Uri returnUri = null;
        int match = myUri.match(uri);
        db = mDataBase.getWritableDatabase();
        if (match == TASK) {
            long result = db.insert("Fav", null, values);
            returnUri = ContentUris.withAppendedId(CONTENT_URI, result);
        }
        // TODO: Implement this to handle requests to insert a new row.
        //throw new UnsupportedOperationException("Not yet implemented");
        getContext().getContentResolver().notifyChange(uri, null);

        return uri;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        Context context = getContext();
        mDataBase = new DataBase(context);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Uri returnUri = null;
        Cursor result = null;
        int match = myUri.match(uri);
        db = mDataBase.getReadableDatabase();
        if (match == TASK) {
            result = db.query("Fav", projection, selection, selectionArgs, null, null, sortOrder);
            // TODO: Implement this to handle query requests from clients.
        }
        return result;


    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
