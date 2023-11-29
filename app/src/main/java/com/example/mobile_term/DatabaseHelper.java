/*
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
 */
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
                "player_name TEXT, team_name TEXT, avg TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스 업그레이드를 세련되게 처리
        Log.w(DatabaseHelper.class.getName(), "데이터베이스를 버전 " + oldVersion + "에서 " +
                newVersion + "(으)로 업그레이드합니다. 기존 데이터가 모두 손실됩니다.");

        // 실제 스키마 변경에 기반한 더 복잡한 마이그레이션 로직을 여기에 추가할 수 있습니다.
        if (oldVersion < 2) {
            // 예시: 2번 버전에서 새 열을 추가합니다.
            db.execSQL("ALTER TABLE baseball_results ADD COLUMN new_column TEXT;");
        }

        // 기존 테이블이 존재하는 경우 해당 테이블을 삭제합니다.
        db.execSQL("DROP TABLE IF EXISTS baseball_results");

        // 새로운 스키마로 테이블을 다시 만듭니다.
        onCreate(db);
    }
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("baseball_results", null, null);
        // auto-incrementing ID를 1부터 다시 시작하도록 재설정
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'baseball_results'");

        db.close();
    }
}
