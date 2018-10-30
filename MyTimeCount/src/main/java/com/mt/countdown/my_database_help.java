package com.mt.countdown;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class my_database_help extends SQLiteOpenHelper {
    private String CREATE_TABLE = "create table mytime(title text PRIMARY KEY,time text)";
    private final static int VERSION = 1;

    public my_database_help(Context context) {
        super(context, "mytime", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
