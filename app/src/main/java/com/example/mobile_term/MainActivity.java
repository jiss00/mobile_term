package com.example.mobile_term;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kbo_rankings);

        new FetchBaseballResultsTask().execute();
    }
    public void onHitterRankingButtonClick(View view) {
        new FetchBaseballResultsTask().execute("https://www.koreabaseball.com/Record/Player/HitterBasic/BasicOld.aspx?sort=HRA_RT");
    }

    public void onPitcherRankingButtonClick(View view) {
        new FetchBaseballResultsTask().execute("https://www.koreabaseball.com/Record/Player/PitcherBasic/BasicOld.aspx?sort=ERA_RT");
    }

    private class FetchBaseballResultsTask extends AsyncTask<String, Void, List<BaseballResult>> {
        @Override
        protected List<BaseballResult> doInBackground(String... urls) {
            if (urls.length == 0) return null;

            String url = urls[0];
            try {
                WebCrawler webCrawler = new WebCrawler();
                return webCrawler.crawlBaseballResults(url);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<BaseballResult> baseballResults) {
            if (baseballResults != null) {
                // 결과를 SQLite 데이터베이스에 저장하는 로직 추가
                DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ListView listView = findViewById(R.id.listViewRankings);
                BaseballResultsAdapter adapter = new BaseballResultsAdapter(MainActivity.this, baseballResults);
                listView.setAdapter(adapter);

                try {
                    //초기화
                    //dbHelper.deleteAllData();
                    for (BaseballResult result : baseballResults) {
                        // ContentValues를 사용하여 데이터베이스에 값을 삽입
                        ContentValues values = new ContentValues();
                        values.put("player_name", result.getPlayerName());
                        values.put("team_name", result.getTeamName());
                        values.put("avg", result.getAvg());

                        // 특정 테이블에 데이터 삽입
                        db.insert("baseball_results", null, values);
                        Log.d("WebCrawler",
                                " Player Name: " + result.getPlayerName() +
                                        ", Team Name: " + result.getTeamName() +
                                        ", Avg: " + result.getAvg());
                    }



                } finally {
                    // 작업이 끝나면 데이터베이스를 닫아야 합니다.
                    db.close();
                }
            }
        }
    }
}