package com.example.mobile_term.PitcherDetailRankings;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_term.R;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String category; // Add a class-level variable for category

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pitcher_rankings);

    }


    public void onPitcherButtonClick(View view) {
        // Get the category from the button's tag
        category = view.getTag().toString();
        String url = "https://sports.news.naver.com/kbaseball/record/index?category=kbo&year=2023";
        new FetchBaseballResultsTask().execute(url);
    }

    private boolean isDataAlreadyInDB(SQLiteDatabase db, BaseballResult result) {
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + getTableName() + " WHERE player_name = ? AND team_name = ? AND value = COALESCE(?, '')",
                new String[]{result.getPlayerName(), result.getTeamName(), result.getValue()}
        );

        try {
            return cursor.getCount() > 0;
        } finally {
            cursor.close();
        }
    }
    private String getTableName() {
        switch (category) {
            case "victory":
                return "pitcher_victory_rankings";
            case "era":
                return "pitcher_era_rankings";
            case "so":
                return "pitcher_so_rankings";
            case "save":
                return "pitcher_save_rankings";
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }
    }

    private class FetchBaseballResultsTask extends AsyncTask<String, Void, List<BaseballResult>> {

        @Override
        protected List<BaseballResult> doInBackground(String... urls) {
            if (urls.length == 0) return null;

            String url = urls[0];
            try {
                WebCrawler webCrawler = new WebCrawler();
                return webCrawler.crawlBaseballResults(url, category);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<BaseballResult> baseballResults) {
            if (baseballResults != null) {
                // 결과를 SQLite 데이터베이스에 저장하는 로직 추가
                DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this, category);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                try {
                    //초기화
                    // dbHelper.deleteAllData();
                    for (BaseballResult result : baseballResults) {
                        // 기존에 있는 데이터와 중복 여부 체크
                        if (!isDataAlreadyInDB(db, result)) {
                            // ContentValues를 사용하여 데이터베이스에 값을 삽입
                            ContentValues values = new ContentValues();
                            values.put("player_name", result.getPlayerName());
                            values.put("team_name", result.getTeamName());
                            values.put("value", result.getValue()); // Use 'value' instead of 'victory'

                            // 특정 테이블에 데이터 삽입
                            db.insert(getTableName(), null, values); // Use dynamic table name
                            Log.d("WebCrawler",
                                    " Player Name: " + result.getPlayerName() +
                                            ", Team Name: " + result.getTeamName() +
                                            ", Value: " + result.getValue());
                        }
                    }
                    // SQLite 데이터베이스에서 데이터를 조회하여 어댑터에 설정
                    List<BaseballResult> dbResults = dbHelper.getAllResults();
                    BaseballResultsAdapter adapter = new BaseballResultsAdapter(MainActivity.this, dbResults);

                    // ListView에 어댑터 설정
                    ListView listView = findViewById(R.id.listView_pitcher_victory);
                    listView.setAdapter(adapter);

                } finally {
                    // 작업이 끝나면 데이터베이스를 닫아야 합니다.
                    db.close();
                }
            }
        }
    }
}
