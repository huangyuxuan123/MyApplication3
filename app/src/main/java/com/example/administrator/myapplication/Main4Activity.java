package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


/**
 * @desc 启动屏
 * Created by devilwwj on 16/1/23.
 */
public class Main4Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Okhttp okhttp=new Okhttp();
                    okhttp.GetaccessToken();
                    okhttp.GetDataandTime();


                }


                catch (Exception e)
                {
                    Log.d("MainActivity", "++++++++++++++++");
                }
            }
        }).start();

        super.onCreate(savedInstanceState);
        // 判断是否是第一次开启应用
        boolean isFirstOpen = SpUtils.getBoolean(this, AppConstants.FIRST_OPEN);
        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpen) {
            Intent intent = new Intent(this, WelcomeGuideActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // 如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.activity_main4);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                enterHomeActivity();
            }
        }, 2000);


    }


    private void enterHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}