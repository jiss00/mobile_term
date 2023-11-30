package com.example.mobile_term.GameResult;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_term.PostSeasonRankings.DatabaseHelper;
import com.example.mobile_term.R;

import java.io.IOException;
import java.util.List;

public class GameResult extends AppCompatActivity {
    Button btn;
    DatePickerDialog datePickerDialog;
    TextView resultTextView;
    DatabaseHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);
        resultTextView = (TextView)findViewById(R.id.textView2);

        new FetchBaseballResultsTask().execute();

        btn = findViewById(R.id.button);
        registerForContextMenu(btn);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu,View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        menu.setHeaderTitle("연도 선택");
        menu.add(0,1,0,"2019");
        menu.add(0,2,0,"2020");
        menu.add(0,3,0,"2021");
        menu.add(0,4,0,"2022");
        menu.add(0,5,0,"2023");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 1:
                resultTextView.setText("1번이노");
                return true;

            case 2:
                resultTextView.setText("2번이노");
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }


    private class FetchBaseballResultsTask extends AsyncTask<Void, Void, List<TeamResult>> {
        @Override
        protected List<TeamResult> doInBackground(Void... voids) {
            try {
                Crawl crawl = new Crawl();

                return crawl.crawlBaseballResults();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(List<TeamResult> teamResults) {
            if (teamResults != null) {
                // 결과를 SQLite 데이터베이스에 저장하는 로직 추가
                DatabaseHelper dbHelper = new DatabaseHelper(GameResult.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                try {
                    for (TeamResult result : teamResults) {
                        // ContentValues를 사용하여 데이터베이스에 값을 삽입
                        ContentValues values = new ContentValues();
                        values.put("rank", result.getRank());
                        values.put("team", result.getTeamName());
                        values.put("play", result.getPlays());
                        values.put("win", result.getWin());
                        values.put("lose", result.getLose());
                        values.put("draw", result.getDraw());


                        // 특정 테이블에 데이터 삽입
                        db.insert("result_2023", null, values);
                        Log.d("WebCrawler",
                                " rank: " + result.getRank() +
                                        ", teamName: " + result.getTeamName() +
                                        ", plays: " + result.getPlays()
                                        +"win: " +result.getWin()
                                        +"lose " + result.getLose()
                                        +"draw " + result.getDraw());
                    }
                } finally {
                    //db.close();
                }


            }
        }
    }
}