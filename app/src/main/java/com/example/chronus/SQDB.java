package com.example.chronus;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SQDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "test_demo.db";
    private static SQLiteDatabase INSTANCE;
    private Context mContext;


    public SQDB(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Remind_List( Type CHAR(10), ID String PRIMARY KEY, " +
                "TITLE String,DAY String ,Content CHAR(20),Checked String,User_name String)");
        db.execSQL("CREATE TABLE List( ID String PRIMARY KEY ,name String  ,icon_color String ," +
                "number String,User_name String)");
        db.execSQL("CREATE TABLE User( User_name String PRIMARY KEY , Password String)");



        db.execSQL("CREATE TABLE Schedule( ID String PRIMARY KEY , Date String, Start_Time String,End_Time String," +
                "Title String ,Place String ,Content String,bg_Color String,User_name String) ");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE Remind_List ADD date newversion ");
    }


}