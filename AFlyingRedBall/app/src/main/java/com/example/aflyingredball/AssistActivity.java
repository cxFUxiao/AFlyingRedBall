package com.example.aflyingredball;
//对应主页上的 帮助
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AssistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assist);
        MainActivity.activityList.add(this);
    }
}