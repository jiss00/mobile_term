package com.example.mobile_term;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "baseball_results.db";
    private static final int DATABASE_VERSION = 3;  // 버전을 증가시킴

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블 생성 쿼리 수정
        db.execSQL("CREATE TABLE baseball_results (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id INTEGER, player_name TEXT, team_name TEXT, avg TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 기존 테이블 삭제
        db.execSQL("DROP TABLE IF EXISTS baseball_results");

        // 새로운 테이블 생성
        onCreate(db);
    }
}
