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
package com.example.mobile_term.HitterDetailRankings;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;

    private String category;

    public DatabaseHelper(Context context, String category) {
        super(context, getDynamicDatabaseName(category), null, DATABASE_VERSION);
        this.category = category;
    }

    private static String getDynamicDatabaseName(String category) {
        return "hitter_" + category + "_rankings.db";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + getTableName() + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "player_name TEXT, team_name TEXT, value TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion);

        // Add your upgrade logic here
        // Example:
        if (oldVersion < 2) {
            // Upgrade from version 1 to 2
            db.execSQL("ALTER TABLE " + getTableName() + " ADD COLUMN new_column TEXT;");
        }
        // Continue with other upgrade steps as needed

        // After the upgrade logic, you may want to drop and recreate the table
        db.execSQL("DROP TABLE IF EXISTS " + getTableName());
        onCreate(db);
    }

    public List<BaseballResult> getAllResults() {
        List<BaseballResult> baseballResults = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + getTableName(), null);

        try {
            int playerNameIndex = cursor.getColumnIndex("player_name");
            int teamNameIndex = cursor.getColumnIndex("team_name");
            int valueIndex = cursor.getColumnIndex("value");

            if (cursor.moveToFirst()) {
                do {
                    if (playerNameIndex != -1) {
                        String playerName = cursor.getString(playerNameIndex);
                        String teamName = cursor.getString(teamNameIndex);
                        String value = cursor.getString(valueIndex);

                        BaseballResult result = new BaseballResult();
                        result.setPlayerName(playerName);
                        result.setTeamName(teamName);
                        result.setValue(value);

                        baseballResults.add(result);
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        return baseballResults;
    }

    private String getTableName() {
        switch (category) {
            case "ba":
                return "hitter_ba_rankings";
            case "rbi":
                return "hitter_rbi_rankings";
            case "hr":
                return "hitter_hr_rankings";
            case "sb":
                return "hitter_sb_rankings";
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }
    }
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(getTableName(), null, null);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + getTableName() + "'");
        db.close();
    }
}
