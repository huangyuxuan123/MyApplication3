package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Switch open1=(Switch)findViewById(R.id.open1);
        Switch open2=(Switch)findViewById(R.id.open2);
        open1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    Toast.makeText(Main3Activity.this,"on1",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Main3Activity.this,"off1",Toast.LENGTH_SHORT).show();
                }
            }
        });

        open2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    Toast.makeText(Main3Activity.this,"on2",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Main3Activity.this,"off2",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
