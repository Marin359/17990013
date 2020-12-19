package com.example.mobilemall.ui.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.example.mobilemall.R;
import com.example.mobilemall.data.database.MallDataBase;
import com.example.mobilemall.data.entity.Account;
import com.example.mobilemall.databinding.ActivityLoginBinding;
import com.example.mobilemall.util.Constant;
import com.google.gson.Gson;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        if ((Constant.account = geAccount()) != null) {
            //已经保存过密码,直接登录
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        initBackgroundVideo();
    }

    /**
     * 初始化背景视频
     */
    private void initBackgroundVideo() {
        activityLoginBinding.video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/raw/login_background"));
        activityLoginBinding.video.setOnPreparedListener(mp -> {
            mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
            mp.start();
            mp.setLooping(true);//循环
        });
        activityLoginBinding.video.start();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReg: {
                //注册
                startActivity(new Intent(this, RegisterActivity.class));
            }
            break;
            case R.id.btnLogin: {
                //登录
                String username = activityLoginBinding.etUserName.getText().toString();
                String password = activityLoginBinding.etPassword.getText().toString();
                new Thread(() -> {
                    Account account = MallDataBase.mallDataBase.accountDao().login(username, password);
                    if (account == null) {
                        toast("用户名或密码错误");
                    } else {
                        Constant.account = account;
                        if (activityLoginBinding.chkPwd.isChecked()) {
                            //记住密码
                            keepLogin(account);
                        }
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    }
                }).start();
            }
            break;

        }
    }

    //记住密码
    private void keepLogin(Account account) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString("user_info", new Gson().toJson(account)).apply();
    }

    //获取保存的用户信息,用于登录
    private Account geAccount() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user_info_json = sharedPreferences.getString("user_info", null);
        if (user_info_json == null) {
            return null;
        } else {
            return new Gson().fromJson(user_info_json, Account.class);
        }
    }
}