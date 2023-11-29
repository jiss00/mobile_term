package com.example.mobile_term.GameResult;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_term.BaseballResult;
import com.example.mobile_term.DatabaseHelper;
import com.example.mobile_term.MainActivity;
import com.example.mobile_term.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GameResult extends AppCompatActivity {
    Button btn;
    Button btn1;
    DatePickerDialog datePickerDialog;
    TextView resultTextView;
    DatabaseHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);
        resultTextView = (TextView) findViewById(R.id.textView2);

        new FetchBaseballResultsTask().execute();

        btn = findViewById(R.id.button);
        registerForContextMenu(btn);
        btn1 = (Button)findViewById(R.id.button2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameResult.this, Home.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("연도 선택");
        menu.add(0, 1, 0, "2019");
        menu.add(0, 2, 0, "2020");
        menu.add(0, 3, 0, "2021");
        menu.add(0, 4, 0, "2022");
        menu.add(0, 5, 0, "2023");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        StringBuilder resultTextBuilder = new StringBuilder();

        switch (item.getItemId()) {
            case 1:
                List<DataModel> result2019 = getData("result_2019");

                result2019.stream()
                                .forEach(dataModel -> {
                                    String rank = dataModel.getRank();
                                    String teamName = dataModel.getTeamName();
                                    String plays = dataModel.getPlays();
                                    String win = dataModel.getWin();
                                    String lose = dataModel.getLose();
                                    String draw = dataModel.getDraw();
                                    resultTextBuilder.append(rank).append("위")
                                            .append(" ").append(teamName).append(" ")
                                            .append(plays).append("경기 ")
                                            .append(win).append("승 ")
                                            .append(lose).append("패 ")
                                            .append(draw).append(("무"))
                                            .append("\n");
                                });
                resultTextView.setText(resultTextBuilder.toString());

                return true;

            case 2:
                List<DataModel> result_2020 = getData("result_2020");

                result_2020.stream()
                        .forEach(dataModel -> {
                            String rank = dataModel.getRank();
                            String teamName = dataModel.getTeamName();
                            String plays = dataModel.getPlays();
                            String win = dataModel.getWin();
                            String lose = dataModel.getLose();
                            String draw = dataModel.getDraw();
                            resultTextBuilder.append(rank).append("위")
                                    .append(" ").append(teamName)
                                    .append(plays).append("경기 ")
                                    .append(win).append("승 ")
                                    .append(lose).append("패 ")
                                    .append(draw).append(("무"))
                                    .append("\n");
                        });
                resultTextView.setText(resultTextBuilder.toString());

                return true;
            case 3:
                List<DataModel> result_2021 = getData("result_2021");

                result_2021.stream()
                        .forEach(dataModel -> {
                            String rank = dataModel.getRank();
                            String teamName = dataModel.getTeamName();
                            String plays = dataModel.getPlays();
                            String win = dataModel.getWin();
                            String lose = dataModel.getLose();
                            String draw = dataModel.getDraw();
                            resultTextBuilder.append(rank).append("위")
                                    .append(" ").append(teamName)
                                    .append(plays).append("경기 ")
                                    .append(win).append("승 ")
                                    .append(lose).append("패 ")
                                    .append(draw).append(("무"))
                                    .append("\n");
                        });
                resultTextView.setText(resultTextBuilder.toString());

                return true;
            case 4:
                List<DataModel> result_2022 = getData("result_2022");

                result_2022.stream()
                        .forEach(dataModel -> {
                            String rank = dataModel.getRank();
                            String teamName = dataModel.getTeamName();
                            String plays = dataModel.getPlays();
                            String win = dataModel.getWin();
                            String lose = dataModel.getLose();
                            String draw = dataModel.getDraw();
                            resultTextBuilder.append(rank).append("위")
                                    .append(" ").append(teamName)
                                    .append(plays).append("경기 ")
                                    .append(win).append("승 ")
                                    .append(lose).append("패 ")
                                    .append(draw).append(("무"))
                                    .append("\n");
                        });
                resultTextView.setText(resultTextBuilder.toString());

                return true;
            case 5:
                List<DataModel> result_2023 = getData("result_2023");

                result_2023.stream()
                        .limit(10)
                        .forEach(dataModel -> {
                            String rank = dataModel.getRank();
                            String teamName = dataModel.getTeamName();
                            String plays = dataModel.getPlays();
                            String win = dataModel.getWin();
                            String lose = dataModel.getLose();
                            String draw = dataModel.getDraw();
                            resultTextBuilder.append(rank).append("위")
                                    .append(" ").append(teamName)
                                    .append(plays).append("경기 ")
                                    .append(win).append("승 ")
                                    .append(lose).append("패 ")
                                    .append(draw).append(("무"))
                                    .append("\n");
                        });
                resultTextView.setText(resultTextBuilder.toString());

                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    public List<DataModel> getData(String table) {
        List<DataModel> dataList = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(GameResult.this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + table, null);
        if (res.moveToFirst()) {
            // 커서에서 데이터 읽기 예제
            do {
                String rank = res.getString(res.getColumnIndex("rank"));
                String teamName = res.getString(res.getColumnIndex("team"));
                String plays = res.getString(res.getColumnIndex("play"));
                String win = res.getString(res.getColumnIndex("win"));
                String lose = res.getString(res.getColumnIndex("lose"));
                String draw = res.getString(res.getColumnIndex("draw"));

                // 여기에서 읽은 데이터를 출력하거나 다른 작업 수행
                Log.d("DatabaseData 확인", "Rank: " + rank +
                        ", Team: " + teamName +
                        ", Plays: " + plays +
                        ", Win: " + win +
                        ", Lose: " + lose +
                        ", Draw: " + draw);
                DataModel dataModel = new DataModel(rank, teamName, plays, win, lose, draw);
                dataList.add(dataModel);

            } while (res.moveToNext());
        }

        // 커서 사용 후 닫기
        //res.close();

        return dataList;

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
                                        + "win: " + result.getWin()
                                        + "lose " + result.getLose()
                                        + "draw " + result.getDraw());
                    }
                } finally {
                    //db.close();
                }


            }
        }
    }
}
