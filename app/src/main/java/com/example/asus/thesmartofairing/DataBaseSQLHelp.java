package com.example.asus.thesmartofairing;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ASUS on 2017/2/21.
 */

public class DataBaseSQLHelp extends SQLiteOpenHelper {

    public static final String CREATE_Register = "create table Register("
    +"id integer primary key autoincrement,"
    +"register_name text,"
    +"register_password text)";

    private Context mContext;
    public DataBaseSQLHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Register);
        Log.d("MainActivity","Creat Success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
