package com.example.aflyingredball;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

//数据库helper类

public class OrderDBHelper extends SQLiteOpenHelper {
    public OrderDBHelper dbHelper;

    public static final String CREATE_BOOK = "create table FractionBook ("
            + "id integer primary key autoincrement, "
            + "numbers integer)";



    private Context mContext;

    public OrderDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        Toast.makeText(mContext, "Database创建成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists FractionBook");

        onCreate(db);
    }

}

