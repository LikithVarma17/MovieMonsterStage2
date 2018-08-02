package com.example.acer.moviemonsters;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Aloha extends AppCompatActivity {
    private static int ALOHA_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aloha);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Aloha.this, MovieActivity.class);
                startActivity(intent);
                finish();
            }
        }, ALOHA_TIME_OUT);


    }
}
