package com.example.administrator.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
        import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.myapplication.MyDatabaseHelper;
        import com.example.administrator.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import com.google.gson.Gson;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button next;
    private Button exit;
    int i = 0;
    private long lastTime=0; //记录上次点击的时间

    Intent intent;

    int count;
    Okhttp okhttp=new Okhttp();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        com.example.administrator.myapplication.DragFloatActionButton button=(com.example.administrator.myapplication.DragFloatActionButton)findViewById(R.id.fb);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button.setOnClickListener(this);




        }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-lastTime)>2000){
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                lastTime=System.currentTimeMillis();
            }else {
                System.exit(0);
            }
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }







    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.button1:


                break;

            case R.id.button2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            okhttp.GetaccessToken();
                            okhttp.post("JX", "JXON", 1);
                        } catch (Exception e) {
                            Log.d("MainActivity", "++++++++++++++++");
                        }
                    }
                }).start();

                break;

            case R.id.button3:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            okhttp.GetaccessToken();
                            okhttp.post("JX", "JXON", 0);
                        } catch (Exception e) {
                            Log.d("MainActivity", "++++++++++++++++");
                        }
                    }
                }).start();

                break;

            case R.id.button4:
                for (int j = 0; j < Okhttp.count; j++) {
                    Log.d("sss", "111:" + Okhttp.wendu[j].getData());
                    Log.d("sss", "222:" + Okhttp.wendu[j].getTime());
                }

                break;

            case  R.id.fb:
                Toast.makeText(MainActivity.this,"hhhhh",Toast.LENGTH_SHORT).show();
        }
    }
}
