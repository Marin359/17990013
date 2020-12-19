package com.example.mobilemall.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class Item {
    //ID
    @PrimaryKey
    @NonNull
    public String uuid;
    //标题
    public String title;
    //详情
    public String content;
    //价格
    public float price;
    //图片名
    public String picture;
    //添加时间
    public long add_datetime;
}
