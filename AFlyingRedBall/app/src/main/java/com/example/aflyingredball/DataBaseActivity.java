package com.example.aflyingredball;
//得分榜
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DataBaseActivity extends Activity {
    private Button mBtnDL;
    private ListView mLv1;
    public OrderDBHelper dbHelper;
    //存查询sqlite出来值的list
    public static List<Score> databaseList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);
        MainActivity.activityList.add(this);
        //查询数据库
        query();

//删除按钮
        mBtnDL = findViewById(R.id.btn_DL);
        dbHelper = new OrderDBHelper(this, "FractionBook.db", null, 1);
        mBtnDL.setOnLongClickListener(new View.OnLongClickListener() {
            @Override//长按
            public boolean onLongClick(View v) {
                Toast.makeText(DataBaseActivity.this, "清空数据", Toast.LENGTH_SHORT).show();
                //清空数据库
                databaseList.clear();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("FractionBook", null,null);
                //跳转成功界面
                Intent intent = new Intent(DataBaseActivity.this, DeleteSuccess.class);
                startActivity(intent);

                return false;
            }
        });

//列表的点击事件
        mLv1 = (ListView) findViewById(R.id.lv_1);
       mLv1.setAdapter(new MyListAdapter(DataBaseActivity.this));
        mLv1.setOnItemClickListener((parent, view, position, id) -> {
            int PositionAddOne = position + 1;
            Toast.makeText(DataBaseActivity.this, "这是你的第" + PositionAddOne + "次得分,加油", Toast.LENGTH_SHORT).show();
            //短按
        });
        mLv1.setOnItemLongClickListener((parent, view, position, id) -> {
            int PositionAddOne = position + 1;
            Toast.makeText(DataBaseActivity.this, "第" + PositionAddOne + "次得分：分数既定，按的再久也没用", Toast.LENGTH_SHORT).show();
            return true;//长按 and终止
        });
    }

//返回的时候清空暂存列表 避免数据重复
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            databaseList.clear();
        }return super.onKeyDown(keyCode, event);

    }


    //query函数
    public void query(){
        dbHelper = new OrderDBHelper(this, "FractionBook.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("FractionBook", null, null, null, null, null, null);
        if (cursor.moveToNext()) {
            do {
                // 遍历Cursor对象 打印log 存在数组
                int pages = cursor.getInt(cursor.getColumnIndex("numbers"));
                Log.d("debug：看分数是否可以查询", "The fraction is " + pages);
                Score score = new Score(pages);
                databaseList.add(score);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }


    }




