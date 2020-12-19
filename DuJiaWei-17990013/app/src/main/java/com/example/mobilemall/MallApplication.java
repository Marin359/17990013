package com.example.mobilemall;

import android.app.Application;

import androidx.room.Room;

import com.example.mobilemall.data.database.MallDataBase;

public class MallApplication extends Application {
    public static MallApplication mallApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mallApplication = this;
        //初始化数据库
        MallDataBase.mallDataBase = Room.databaseBuilder(this, MallDataBase.class, "malldb").build();
    }
}