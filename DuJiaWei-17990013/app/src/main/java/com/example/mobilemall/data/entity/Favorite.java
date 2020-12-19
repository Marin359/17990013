package com.example.mobilemall.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.mobilemall.data.database.convert.ItemConvert;

@Entity(tableName = "favorites", primaryKeys = {"uid", "item_id"})
@TypeConverters({ItemConvert.class})
public class Favorite {
    //用户唯一标识
    @NonNull
    public String uid;
    //收藏的商品ID
    @NonNull
    public String item_id;
    //商品
    public Item item;
    //收藏时间
    public long time;
}
