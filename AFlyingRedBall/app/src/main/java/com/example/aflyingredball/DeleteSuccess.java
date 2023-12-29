package com.example.aflyingredball;
//删除成功的跳转
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;


public class DeleteSuccess extends AppCompatActivity {
private TextView mDes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_success);
        MainActivity.activityList.add(this);
        mDes = findViewById(R.id.tx_des);


        mDes.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Intent intent = new Intent(DeleteSuccess.this, MainActivity.class);
                startActivity(intent);
                return true;
        }});

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(DeleteSuccess.this, MainActivity.class);
                startActivity(intent);
            }
        }, 1000);




        }
    }

