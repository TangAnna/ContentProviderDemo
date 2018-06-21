package com.example.tanganan.contentproviderdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * ContentProvider的使用
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_main_contact).setOnClickListener(this);
        findViewById(R.id.btn_main_photo).setOnClickListener(this);
        findViewById(R.id.btn_main_music).setOnClickListener(this);
        findViewById(R.id.btn_main_video).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_main_contact:
                startActivity(new Intent(this, ContactActivity.class));
                break;
            case R.id.btn_main_photo:
                startActivity(new Intent(this, PhotoActivity.class));
                break;
            case R.id.btn_main_music:
                break;
            case R.id.btn_main_video:
                break;
        }
    }
}
