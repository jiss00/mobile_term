package com.example.mobile_term;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "result_2022.db";
    private static final int DATABASE_VERSION = 1;  // 버전을 증가시킴


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createYearlyTable(db, 2019);
        createYearlyTable(db, 2020);
        createYearlyTable(db, 2021);
        createYearlyTable(db, 2022);
        createYearlyTable(db, 2023);

    }
    private void createYearlyTable(SQLiteDatabase db, int year) {
        String tableName = "result_" + year;
        db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "rank TEXT, "
                + "team TEXT, "
                + "play TEXT, "
                + "win TEXT, "
                + "lose TEXT, "
                + "draw TEXT"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 기존 테이블 삭제
        db.execSQL("DROP TABLE IF EXISTS tableName");

        // 새로운 테이블 생성
        onCreate(db);
    }
}
