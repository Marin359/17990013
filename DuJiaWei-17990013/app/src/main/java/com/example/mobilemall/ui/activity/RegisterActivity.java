package com.example.mobilemall.ui.activity;

import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.mobilemall.R;
import com.example.mobilemall.data.database.MallDataBase;
import com.example.mobilemall.data.entity.Account;
import com.example.mobilemall.databinding.ActivityRegisterBinding;

import java.util.UUID;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private ActivityRegisterBinding activityRegisterBinding;
    private int second = 60;//验证码倒计时

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        initBackgroundVideo();
    }

    /**
     * 初始化背景视频
     */
    private void initBackgroundVideo() {
        activityRegisterBinding.video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/raw/login_background"));
        activityRegisterBinding.video.setOnPreparedListener(mp -> {
            mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
            mp.start();
            mp.setLooping(true);//循环
        });
        activityRegisterBinding.video.start();
    }


    private final Runnable runnable = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            second--;
            if (second < 0) {
                second = 60;
                activityRegisterBinding.btnGetCode.setText("获取验证码");
                return;
            }
            activityRegisterBinding.btnGetCode.setText(second + "秒");
            handler.postDelayed(runnable, 1000);//更新验证码按钮文字
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGetCode: {
                //获取验证码
                if (second != 60) {
                    //还不可以获取验证码
                    return;
                }
                second = 60;
                handler.postDelayed(runnable, 1000);//更新验证码按钮文字
            }
            break;
            case R.id.btnBack: {
                //返回
                finish();
            }
            break;
            case R.id.btnReg: {//注册按钮事件
                //获取注册信息
                String username = activityRegisterBinding.etUserName.getText().toString();
                String password = activityRegisterBinding.etPassword.getText().toString();
                String code = activityRegisterBinding.etCode.getText().toString();
                //检查内容合法性
                if (username.length() == 0 || password.length() == 0 || code.length() == 0) {
                    toast("请将注册信息填写完整");
                    return;
                }
                if (code.length() != 6) {
                    toast("验证码输入有误");
                    return;
                }
                new Thread(() -> {
                    if (MallDataBase.mallDataBase.accountDao().checkMobile(username).size() > 0) {
                        toast("该手机号已被注册，请换一个");
                        return;
                    }
                    Account account = new Account();
                    account.password = password;
                    account.mobile = username;
                    account.uid = UUID.randomUUID().toString().replaceAll("-", "");
                    //新增用户
                    MallDataBase.mallDataBase.accountDao().register(account);
                    toast("注册成功");
                    finish();
                }).start();
            }
            break;
        }
    }
}