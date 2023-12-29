package com.example.aflyingredball;
//这是主页面
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private long exitTime = 0;
    private Button mBtnST;
    private Button mBtABOUT;
    private TextView mTvwc;
    private Button mBtnHp;
    private Button mBtndb;
   // private Button mBtnSET;
    private pl.droidsonroids.gif.GifImageView mEgg;
    public static List<Activity> activityList = new LinkedList();
    Score sound=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.activityList.add(this);

        mBtABOUT = (Button) findViewById(R.id.btn_about);
        mBtnST = (Button) findViewById(R.id.btn_ST);
        mBtnHp = (Button) findViewById(R.id.btn_help);
        mBtndb = (Button) findViewById(R.id.btn_db);
       // mBtnSET = (Button) findViewById(R.id.btn_set);
        mEgg = findViewById(R.id.gif_ball);
        mTvwc = findViewById(R.id.tx_welcome);
        mTvwc.setSelected(true);

        OnClick onClick = new OnClick();

        mBtnST.setOnClickListener(onClick);
        mBtABOUT.setOnClickListener(onClick);
        mTvwc.setOnClickListener(onClick);
        mEgg.setOnClickListener(onClick);
        mBtnHp.setOnClickListener(onClick);
        mBtndb.setOnClickListener(onClick);
       // mBtnSET.setOnClickListener(onClick);

    }
    //这里是点击事件
    class OnClick implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_ST:

                    intent = new Intent(MainActivity.this, prepare.class);
                    startActivity(intent);
                    break;

                case R.id.btn_about:
                    Toast toastCustom = new Toast(getApplicationContext());
                    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                    View view = inflater.inflate(R.layout.layout_toast, null);
                    ImageView imageView = (ImageView) view.findViewById(R.id.iv_toast);
                    TextView textView = (TextView) view.findViewById(R.id.tv_toast);
                    imageView.setImageResource(R.drawable.icon_good);
                    textView.setText("物联网181 张晨浩");
                    toastCustom.setView(view);
                    toastCustom.setDuration(Toast.LENGTH_LONG);
                    toastCustom.show();
                    break;

                case R.id.tx_welcome:
                    intent = new Intent(MainActivity.this, HelpActivity.class);
                    startActivity(intent);
                    break;
                case R.id.gif_ball:
                    Toast.makeText(getApplicationContext(), "发现了彩蛋( •̀ ω •́ )✧✌   耶~~~~", Toast.LENGTH_LONG).show();
                    break;
                case R.id.btn_help:
                    intent = new Intent(MainActivity.this, AssistActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_db:
                    intent = new Intent(MainActivity.this, DataBaseActivity.class);
                    startActivity(intent);
                    break;
        //       case R.id.btn_set:
        //           intent = new Intent(MainActivity.this, SettingsActivity.class);
        //           startActivity(intent);
        //           break;
            }
        }
    }

    //试一下双击退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();

            } else {
                finish();
                exit();
            }MainActivity.activityList.add(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void exit(){

        for(Activity act:activityList){
            act.finish();
        }
        System.exit(0); }
   //成功了！！！！！！！！




}