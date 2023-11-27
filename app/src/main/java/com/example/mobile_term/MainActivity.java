package com.example.mobile_term;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


import org.w3c.dom.Document;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private Button crawlButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);
        crawlButton = findViewById(R.id.crawlButton);

        crawlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 시 크롤링 시작
                crawl();
            }
        });
    }

    private void crawl() {
        // 새로운 쓰레드 생성
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 웹 크롤링 수행
                String url = "https://www.koreabaseball.com/Record/Player/HitterBasic/BasicOld.aspx?sort=HRA_RT"; // 크롤링할 웹 페이지 주소
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
            Elements elements = doc.select("div.record_result table tbody tr"); // tbody 태그 안의 모든 tr 태그를 선택
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
    });
}