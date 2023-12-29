package com.example.aflyingredball;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

//设置 还没做出来

public class SettingsActivity extends AppCompatActivity {


        private RadioGroup mRg1;
        private RadioButton ylk;
        private RadioButton ylg;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings);
            MainActivity.activityList.add(this);
            Score sound=null;
            Score speed=null;

            mRg1 = (RadioGroup) findViewById(R.id.rg_yinliang);
            ylk = findViewById(R.id.rb_yinKai);
            ylg = findViewById(R.id.rb_yinGuan);

            mRg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                    RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                    Toast.makeText(SettingsActivity.this, radioButton.getText(), Toast.LENGTH_SHORT).show();

                    switch (checkedId) {
                        case R.id.rb_yinKai:
                         //   sound.setsound(1);
                            break;
                        case R.id.rb_yinGuan:
                        //    sound.setsound(0);
                            break;

                        case R.id.suKuai:
                        //    speed.setspeeed((float) 3.5);
                            break;
                        case R.id.suMan:
                       //     speed.setspeeed((float) 2.0);
                            break;

                        default:
                            break;
                    }
                }
            });

            }
        }



