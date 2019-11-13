package com.example.administrator.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018/11/5.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    // 重写构造方法
    public MySQLiteOpenHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory cursor, int version) {
        super(context, name, cursor, version);
    }

    // 创建数据库的方法
    public void onCreate(SQLiteDatabase db) {
// 创建一个数据库，表名：imagetable，字段：_id、image。
        db.execSQL("CREATE TABLE imagetable (_id INTEGER PRIMARY KEY AUTOINCREMENT,image BLOB)");
    }

    // 更新数据库的方法
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    }

