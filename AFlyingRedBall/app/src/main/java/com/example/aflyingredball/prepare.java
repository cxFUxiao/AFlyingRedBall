package com.example.aflyingredball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

//准备页
public class prepare extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare);
        MainActivity.activityList.add(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(prepare.this, MainGameActivity.class);
                startActivity(intent);
            }
        }, 4000);



    }



    }
