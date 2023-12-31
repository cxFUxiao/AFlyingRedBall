package com.example.aflyingredball;
//游戏页面
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import android.media.AudioManager;
import android.widget.Toast;

import android.widget.CompoundButton;
import android.widget.Switch;


public class MainGameActivity extends AppCompatActivity {

    int points;
    boolean iswaste=false;


    int screen_height;
    int screen_width;

    float qiu_size = 30;
    float qiu_downspeed = 0.32f;
    float qiu_upspeed = 120;
    float qiu_x = 200;
    float qiu_y;

    float zhu_height1;
    float zhu_height2;
    float zhu_width1;
    float zhu_width2;
    float zhu_x1;
    float zhu_x2;
    float zhu_y1;
    float zhu_y2;
    float zhu_xspeed = 0.75f;
    public OrderDBHelper dbHelper;

    GameView gameView;
    Timer timer;
    boolean flag;
    SoundUtils soundUtils;

    private int originalVolume; // 保存原始音量

    private boolean isSoundOn = true; // 声音开关，默认开启

    // 添加声音开关的方法
    public void toggleSound() {
        isSoundOn = !isSoundOn;
        Toast.makeText(this, isSoundOn ? "声音已开启" : "声音已关闭", Toast.LENGTH_SHORT).show();

        // 更新音效状态
        if (isSoundOn) {
            // 恢复音量
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);
        } else {
            // 静音
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainActivity.activityList.add(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);



        // 获取 Switch 组件实例
        Switch soundSwitch = findViewById(R.id.soundSwitch);




        // 打印 Switch 对象的引用
        Log.d("SwitchVisibility", "Switch object: " + soundSwitch);

        // 打印 Switch 的可见性
        Log.d("SwitchVisibility", "Switch visibility before: " + soundSwitch.getVisibility());

        // 设置 Switch 的可见性为可见
        soundSwitch.setVisibility(View.VISIBLE);

        // 打印 Switch 的可见性，确保设置成功
        Log.d("SwitchVisibility", "Switch visibility after: " + soundSwitch.getVisibility());




        // 设置 Switch 的初始状态
        soundSwitch.setChecked(isSoundOn);

        // 设置 Switch 的点击事件
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 切换声音开关状态
                toggleSound();
            }
        });



        //开局设置音量
        /*setVolumeControlStream(AudioManager.STREAM_MUSIC);
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int targetVolume = (int) (0.5 * maxVolume); // 50% 的音量
        originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC); // 保存原始音量
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, targetVolume, 0);*/

        //绑定创建的游戏视图
        gameView = new GameView(this);
        setContentView(gameView);

        //打开database
        dbHelper = new OrderDBHelper(this, "FractionBook.db", null, 1);
        dbHelper.getWritableDatabase();

        //获取窗口管理器
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        //创建音效实例
        soundUtils = new SoundUtils(MainGameActivity.this, SoundUtils.RING_SOUND);

        //获取屏幕宽 高
        screen_height = metrics.heightPixels;
        screen_width = metrics.widthPixels;
        //开局
        play();
        }


        public void gameaction(){
            qiu_y = qiu_y - qiu_upspeed;

            soundUtils.putSound(1, R.raw.duang);
            soundUtils.playSound(1, SoundUtils.SINGLE_PLAY);
            //消息
            handler.sendEmptyMessage(666);

        }



    //返回的时候计分 避免遗漏 返回主页
    //按键事件增加动作控制
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) && event.getAction() == KeyEvent.ACTION_DOWN) {
            // 处理音量键事件，执行类似点击屏幕的操作
            gameaction();
            return true;  // 返回true表示已经处理该事件
        } else if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (!iswaste) {
                jifen();
            }
            Intent intent = new Intent(MainGameActivity.this, MainActivity.class);
            startActivity(intent);
            return true;  // 返回true表示已经处理该事件
        }

        return super.onKeyDown(keyCode, event);
    }





    //游戏开局
    @SuppressLint("ClickableViewAccessibility")
    public void play() {
        iswaste = false;
        //柱子初始位置 长宽   [x,y]  x+Math.random()*y%(y-x+1)   [0,screen_height - 200]  [100,500]
        zhu_height1 = (float) (Math.random() * (screen_height - 200) % (screen_height - 199));
        zhu_width1 = 100 + (float) (Math.random() * 500 % 401);
        zhu_x1 = screen_width;
        zhu_y1 = 0;

        zhu_height2 = screen_height - zhu_height1 - 200;
        zhu_width2 = zhu_width1;
        zhu_x2 = screen_width;
        zhu_y2 = screen_height;

        //球初始y位置
        qiu_y = screen_height >> 1;

        //初始分数
        points = 0;

        //初始分数标识
        flag = true;

        //设置监听手势
        gameView.setOnTouchListener(touchListener);

        //设置标识，启动绘制初始画面
        handler.sendEmptyMessage(666);

        //之后的画面绘制
        //定时器，启动绘制
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //球与柱的位置变化
                qiu_y = qiu_y + qiu_downspeed;
                zhu_x1 = zhu_x1 - zhu_xspeed;
                zhu_x2 = zhu_x2 - zhu_xspeed;

                //球碰撞柱子
                if (qiu_x >= zhu_x1 && qiu_x <= zhu_x1 + zhu_width1) {
                    if (qiu_y < zhu_height1 || qiu_y > zhu_height1 + 200) {
                        iswaste = true;
                        jifen();
                        timer.cancel();
                    }
                }

                //球碰撞上下边缘
                if (qiu_y >= screen_height || qiu_y <= 0) {
                    iswaste = true;
                    jifen();
                    timer.cancel();
                }

                //积分判断
                if (flag) {
                    if (qiu_x > zhu_x1 + zhu_width1) {
                        points++;
                        flag = false;}
                }

                //柱子右侧移出左屏幕时，柱子回档
                if (zhu_x1 + zhu_width1 <= 0) {
                    //柱子回档到最右边，作为下一个柱子
                    zhu_x1 = screen_width;
                    zhu_x2 = screen_width;

                    //随机下一个柱子的长 宽
                    zhu_height1 = (float) (Math.random() * (screen_height - 200) % (screen_height - 199));
                    zhu_width1 = 100 + (float) (Math.random() * 500 % 401);

                    zhu_height2 = screen_height - zhu_height1 - 200;
                    zhu_width2 = zhu_width1;

                    flag = true;

                }

                //变化后重绘
                handler.sendEmptyMessage(666);
            }
        }, 0, 1);

    }



    //手势监听
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
             gameaction();
            }
            return true;
        }


    };





    //判断标识启动绘制
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 666) {
                gameView.invalidate();
            }
        }
    };

    //画板
    class GameView extends View {
        //创建画笔
        Paint paint = new Paint();

        public GameView(Context context) {
            super(context);
        }


        //画布
        @SuppressLint({"ClickableViewAccessibility", "ResourceAsColor"})
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            //画笔属性
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);//抗锯齿



                //绘制操作
                if (iswaste) {

                    paint.setColor(Color.RED);
                    paint.setTextSize(80);
                    paint.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText("游戏结束，你获得了" + points + "分", screen_width >> 1, (screen_height >> 1) - 40, paint);
                    canvas.drawText("点击屏幕重新开始", screen_width >> 1, (screen_height >> 1) + 60, paint);

                    //点击音效
                      soundUtils.putSound(1, R.raw.qiaoluo);
                      soundUtils.playSound(1, SoundUtils.SINGLE_PLAY);//}

                    //任意位置重新开始
                    this.setOnTouchListener(new OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                play();
                            }
                            return true;
                        }
                    });

                } else {


                    //画球
                    canvas.drawCircle(qiu_x, qiu_y, qiu_size, paint);

                    //画柱
                    int color1 = getResources().getColor(R.color.zhuzi);
                    paint.setColor(color1);
                    canvas.drawRect(zhu_x1, zhu_y1, zhu_width1 + zhu_x1, zhu_height1 + zhu_y1, paint);
                    canvas.drawRect(zhu_x2, zhu_y2 - zhu_height2, zhu_width2 + zhu_x2, zhu_height2 + zhu_y2, paint);

                    //画分数
                    paint.setColor(Color.RED);
                    paint.setTextSize(80);
                    paint.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText(points + "", screen_width >> 1, 80, paint);

                }

            }

        }

    public void jifen(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("numbers", points);
        db.insert("FractionBook", null, values);}


    //public void shengyinKai(){
       // sound=true;}
   // public void shengyinGuan(){
      //  sound=false;}


    //恢复音量
    @Override
    protected void onPause() {
        super.onPause();
        // 在 Activity 暂停时，恢复原始音量
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在 Activity 销毁时，确保再次恢复原始音量
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);
    }
}







