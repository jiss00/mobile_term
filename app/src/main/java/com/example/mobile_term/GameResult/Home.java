package com.example.mobile_term.GameResult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.mobile_term.PostSeasonRankings.MainActivity;
import com.example.mobile_term.R;

public class Home extends AppCompatActivity {
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageView1 = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView1);
        imageView3 = (ImageView) findViewById(R.id.imageView2);

        imageView1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Home.this,GameResult.class);
                startActivity(intent);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Home.this, MainActivity.class);
                startActivity(intent);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Home.this, com.example.mobile_term.DetailRankings.MainActivity.class);
                startActivity(intent);
            }
        });


    }
}