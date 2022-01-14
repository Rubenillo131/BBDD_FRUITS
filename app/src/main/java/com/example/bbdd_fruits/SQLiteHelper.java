package com.example.bbdd_fruits;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TheFruitis.db";
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS fruitis(_id integer PRIMARY KEY, name text,weight integer, type text, rotten boolean);";
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS fruitis";

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { db.execSQL(CREATE_TABLE); }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        db.execSQL(DELETE_TABLE);
    }
}