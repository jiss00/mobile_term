package com.example.mobile_term.GameResult;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.mobile_term.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameResult extends AppCompatActivity {
    Button btn;
    Button btn1;
    DatePickerDialog datePickerDialog;
    TextView resultTextView;
    DbHelper helper;
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
        SpannableStringBuilder resultTextBuilder = new SpannableStringBuilder();
        Map<String, Integer> teamLogoMap = new HashMap<>();
        teamLogoMap.put("KT", R.drawable.kt);
        teamLogoMap.put("두산", R.drawable.dusan);
        teamLogoMap.put("삼성", R.drawable.samsung);
        teamLogoMap.put("LG", R.drawable.lg);
        teamLogoMap.put("키움", R.drawable.kium);
        teamLogoMap.put("SSG", R.drawable.ssg);
        teamLogoMap.put("NC", R.drawable.nc);
        teamLogoMap.put("롯데", R.drawable.lotte);
        teamLogoMap.put("KIA", R.drawable.kia);
        teamLogoMap.put("한화", R.drawable.hanhwa);

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
                                    Integer teamLogoResourceId = teamLogoMap.get(teamName);
                                    if (teamLogoResourceId != null) {
                                        Drawable teamLogoDrawable = ContextCompat.getDrawable(this, teamLogoResourceId);
                                        if (teamLogoDrawable != null) {
                                            teamLogoDrawable.setBounds(0, 20,80,80);

                                            ImageSpan imageSpan = new ImageSpan(teamLogoDrawable, ImageSpan.ALIGN_BASELINE);

                                            // 팀 로고를 추가할 위치 계산
                                            int logoPosition = rank.length() + 1 + teamName.length() + 1; // rank, 공백, teamName 다음에 로고를 추가

                                            // 나머지 텍스트 추가
                                            resultTextBuilder.append(rank).append("위")
                                                    .append(" ").append(teamName)
                                                    .append(" ");
                                            resultTextBuilder.setSpan(imageSpan, resultTextBuilder.length()-1, resultTextBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                                            resultTextBuilder.append(plays).append("경기 ")
                                                    .append(win).append("승 ")
                                                    .append(lose).append("패 ")
                                                    .append(draw).append(("무"))
                                                    .append("\n");
                                        }
                                    }

                                    // 결과를 텍스트뷰에 설정
                                    resultTextView.setText(resultTextBuilder);
                                });

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
                            Integer teamLogoResourceId = teamLogoMap.get(teamName);
                            if (teamLogoResourceId != null) {
                                Drawable teamLogoDrawable = ContextCompat.getDrawable(this, teamLogoResourceId);
                                if (teamLogoDrawable != null) {
                                    teamLogoDrawable.setBounds(0, 20,80,80);

                                    ImageSpan imageSpan = new ImageSpan(teamLogoDrawable, ImageSpan.ALIGN_BASELINE);

                                    // 팀 로고를 추가할 위치 계산
                                    int logoPosition = rank.length() + 1 + teamName.length() + 1; // rank, 공백, teamName 다음에 로고를 추가

                                    // 나머지 텍스트 추가
                                    resultTextBuilder.append(rank).append("위")
                                            .append(" ").append(teamName)
                                            .append(" ");
                                    resultTextBuilder.setSpan(imageSpan, resultTextBuilder.length()-1, resultTextBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                                    resultTextBuilder.append(plays).append("경기 ")
                                            .append(win).append("승 ")
                                            .append(lose).append("패 ")
                                            .append(draw).append(("무"))
                                            .append("\n");
                                }
                            }

                            // 결과를 텍스트뷰에 설정
                            resultTextView.setText(resultTextBuilder);
                        });

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
                            Integer teamLogoResourceId = teamLogoMap.get(teamName);
                            if (teamLogoResourceId != null) {
                                Drawable teamLogoDrawable = ContextCompat.getDrawable(this, teamLogoResourceId);
                                if (teamLogoDrawable != null) {
                                    teamLogoDrawable.setBounds(0, 20,80,80);

                                    ImageSpan imageSpan = new ImageSpan(teamLogoDrawable, ImageSpan.ALIGN_BASELINE);

                                    // 팀 로고를 추가할 위치 계산
                                    int logoPosition = rank.length() + 1 + teamName.length() + 1; // rank, 공백, teamName 다음에 로고를 추가

                                    // 나머지 텍스트 추가
                                    resultTextBuilder.append(rank).append("위")
                                            .append(" ").append(teamName)
                                            .append(" ");
                                    resultTextBuilder.setSpan(imageSpan, resultTextBuilder.length()-1, resultTextBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                                    resultTextBuilder.append(plays).append("경기 ")
                                            .append(win).append("승 ")
                                            .append(lose).append("패 ")
                                            .append(draw).append(("무"))
                                            .append("\n");
                                }
                            }

                            // 결과를 텍스트뷰에 설정
                            resultTextView.setText(resultTextBuilder);
                        });

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
                            Integer teamLogoResourceId = teamLogoMap.get(teamName);
                            if (teamLogoResourceId != null) {
                                Drawable teamLogoDrawable = ContextCompat.getDrawable(this, teamLogoResourceId);
                                if (teamLogoDrawable != null) {
                                    teamLogoDrawable.setBounds(0, 20,80,80);

                                    ImageSpan imageSpan = new ImageSpan(teamLogoDrawable, ImageSpan.ALIGN_BASELINE);

                                    // 팀 로고를 추가할 위치 계산
                                    int logoPosition = rank.length() + 1 + teamName.length() + 1; // rank, 공백, teamName 다음에 로고를 추가

                                    // 나머지 텍스트 추가
                                    resultTextBuilder.append(rank).append("위")
                                            .append(" ").append(teamName)
                                            .append(" ");
                                    resultTextBuilder.setSpan(imageSpan, resultTextBuilder.length()-1, resultTextBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                                    resultTextBuilder.append(plays).append("경기 ")
                                            .append(win).append("승 ")
                                            .append(lose).append("패 ")
                                            .append(draw).append(("무"))
                                            .append("\n");
                                }
                            }

                            // 결과를 텍스트뷰에 설정
                            resultTextView.setText(resultTextBuilder);
                        });

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
                            Integer teamLogoResourceId = teamLogoMap.get(teamName);
                            if (teamLogoResourceId != null) {
                                Drawable teamLogoDrawable = ContextCompat.getDrawable(this, teamLogoResourceId);
                                if (teamLogoDrawable != null) {
                                    teamLogoDrawable.setBounds(0, 20,80,80);

                                    ImageSpan imageSpan = new ImageSpan(teamLogoDrawable, ImageSpan.ALIGN_BASELINE);

                                    // 팀 로고를 추가할 위치 계산
                                    int logoPosition = rank.length() + 1 + teamName.length() + 1; // rank, 공백, teamName 다음에 로고를 추가

                                    // 나머지 텍스트 추가
                                    resultTextBuilder.append(rank).append("위")
                                            .append(" ").append(teamName)
                                            .append(" ");
                                    resultTextBuilder.setSpan(imageSpan, resultTextBuilder.length()-1, resultTextBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                                    resultTextBuilder.append(plays).append("경기 ")
                                            .append(win).append("승 ")
                                            .append(lose).append("패 ")
                                            .append(draw).append(("무"))
                                            .append("\n");
                                }
                            }

                            // 결과를 텍스트뷰에 설정
                            resultTextView.setText(resultTextBuilder);
                        });

                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    public List<DataModel> getData(String table) {
        List<DataModel> dataList = new ArrayList<>();
        DbHelper helper = new DbHelper(GameResult.this);
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
                DbHelper dbHelper = new DbHelper(GameResult.this);
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
