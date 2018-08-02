package com.example.acer.moviemonsters;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context) {
        super(context, "Fav", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String query = "CREATE TABLE Fav ( id INTEGER PRIMARY KEY,language TEXT NOT NULL,Bgposter TEXT NOT NULL,Frposter TEXT NOT NULL,title TEXT NOT NULL,rating REAL NOT NULL,votes INTEGER NOT NULL,releasedate TEXT NOT NULL,overview TEXT NOT NULL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean search(int id) {
        SQLiteDatabase db = getReadableDatabase();
        int num = db.rawQuery("SELECT * FROM Fav where id=" + id, null).getCount();
        if (num == 0) {
            return false;
        } else {
            return true;
        }
    }
}
