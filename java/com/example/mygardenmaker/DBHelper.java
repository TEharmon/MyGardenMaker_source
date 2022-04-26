package com.example.mygardenmaker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=4;
    public DBHelper(Context context){
        super(context,"gardendb",null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String gardenSQL="create table tb_garden ("+"_id integer primary key autoincrement, " +
                "title, "+"period, "+"startdate, "+"enddate)";
        db.execSQL(gardenSQL);
        String complete="create table tb_complete ("+"_id integer primary key autoincrement, "+"title)";
        db.execSQL(complete); //새로운 디비하나 더 추가 tb_complete 완료된 것만 보관하는 DB
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if (newVersion == DATABASE_VERSION) {
            db.execSQL("drop table tb_garden");
            onCreate(db);
        }
    }
} //dbhelper 교체하기
