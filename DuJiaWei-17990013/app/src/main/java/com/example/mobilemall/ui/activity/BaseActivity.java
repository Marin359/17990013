package com.example.mobilemall.ui.activity;

import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity {
    public Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void toast(String msg) {
        handler.post(() -> {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });
    }
}
