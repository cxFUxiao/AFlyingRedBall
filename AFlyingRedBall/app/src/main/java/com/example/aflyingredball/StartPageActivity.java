package com.example.aflyingredball;
//启动页 如果没有数据库文件则在此创建
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;


public class StartPageActivity extends AppCompatActivity {
    public OrderDBHelper dbHelper;
    private View mTou;

    public int sign2 = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        MainActivity.activityList.add(this);
        mTou = findViewById(R.id.touch);


        //创建database
        dbHelper = new OrderDBHelper(this, "FractionBook.db", null, 1);
        dbHelper.getWritableDatabase();

        //touch跳过
        mTou.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Intent intent = new Intent(StartPageActivity.this, MainActivity.class);
                startActivity(intent);
                sign2 = 1;
                return false;
            }
        });


        //设置延时
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                switch (sign2) {
                    case 0:
                        Intent intent = new Intent(StartPageActivity.this, MainActivity.class);
                        startActivity(intent);

                        break;
                }
            }
        }, 2000);

    }

}



