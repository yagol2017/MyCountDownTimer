package com.mt.countdown;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class database {
    private SQLiteDatabase db;
    private my_database_help my_database_help = null;
    private ArrayList<databean> bean = new ArrayList<>();
    private databean tempbean = new databean();
    private Cursor cursor;
    private String SELECT_DATA = "select title,time from mytime";
    private String INSERT_DATA = "insert into mytime(title,time) values(?,?)";
    private String UPDATE_DATA = "update mytime set time=? where title=?";

    public database(Context context) {
        my_database_help = new my_database_help(context);
    }

    public ArrayList<databean> getBean() {
        db = my_database_help.getWritableDatabase();
        cursor = db.rawQuery(SELECT_DATA, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tempbean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            tempbean.setTime(cursor.getString(cursor.getColumnIndex("time")));
            bean.add(tempbean);
            tempbean = new databean();//重初始化定义tempbean，如果不，可能会出现bean中重复数据 TODO：unknow
            cursor.moveToNext();
        }
        db.close();
        my_database_help.close();
        return bean;
    }

    public void addData(databean databean) {
        db = my_database_help.getWritableDatabase();
        db.execSQL(INSERT_DATA, new String[]{databean.getTitle(), databean.getTime()});
        db.close();
        my_database_help.close();
    }

    public void deleteData(String title) {
        db = my_database_help.getWritableDatabase();
        db.execSQL("delete from mytime where title='" + title + "'");
        db.close();
        my_database_help.close();
    }

    public void deleteall() {
        db = my_database_help.getWritableDatabase();
        db.execSQL("delete from mytime");
        db.close();
        my_database_help.close();
    }

    public void updateData(databean databean) {
        db = my_database_help.getWritableDatabase();
        db.execSQL(UPDATE_DATA, new String[]{databean.getTime(), databean.getTitle()});
        db.close();
        my_database_help.close();
    }
}
