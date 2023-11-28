package com.example.mobile_term.GameResult;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_term.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Calendar;

public class GameResult extends AppCompatActivity {
    Button btn;
    DatePickerDialog datePickerDialog;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        resultTextView = findViewById(R.id.textView2);
        crawl();
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    public void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Handle the selected date here if needed

            }
        }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    private void crawl() {
        // 새로운 쓰레드 생성
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 웹 크롤링 수행
                String url = "https://sports.news.naver.com/kbaseball/record/index?category=kbo&year=2023"; // 크롤링할 웹 페이지 주소
                String result = performJsoup(url);

                // 메인 쓰레드로 결과 전달
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }).start();
    }

    private String performJsoup(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("div.rr_wrap div.tbl_box table tbody#regularTeamRecordList_table tr"); // tbody 태그 안의 모든 tr 태그를 선택
            StringBuilder resultText = new StringBuilder();

            for (org.jsoup.nodes.Element element : elements) {
                resultText.append(element.text()).append("\n"); // 각 tr 요소의 텍스트를 추출하고 개행 추가
            }

            return resultText.toString(); // 추출한 텍스트 반환
        } catch (IOException e) {
            return "Error during crawling";
        }
    }

    // 핸들러 정의
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            // 메인 쓰레드에서 결과를 받아 처리
            String result = msg.getData().getString("result");
            resultTextView.setText(result);
            return true;
        }

    }
    );
}

