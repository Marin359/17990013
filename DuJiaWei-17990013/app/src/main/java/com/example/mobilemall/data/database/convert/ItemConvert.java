package com.example.mobilemall.data.database.convert;

import androidx.room.TypeConverter;

import com.example.mobilemall.data.entity.Item;
import com.google.gson.Gson;

/**
 * 将商品实体通过JSON形式存到数据库
 */
public class ItemConvert {
    @TypeConverter
    public static Item fromString(String value) {
        return new Gson().fromJson(value, Item.class);
    }

    @TypeConverter
    public static String fromArrayList(Item item) {
        Gson gson = new Gson();
        return gson.toJson(item);
    }
}
