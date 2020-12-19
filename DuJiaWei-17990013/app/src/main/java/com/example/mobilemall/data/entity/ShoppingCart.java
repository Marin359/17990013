package com.example.mobilemall.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.mobilemall.data.database.convert.ItemConvert;

/**
 * 购物车记录
 */
@Entity(tableName = "shopping_carts", primaryKeys = {"uid", "item_uuid"})
@TypeConverters({ItemConvert.class})
public class ShoppingCart {
    /**
     * 用户唯一标识
     */
    @NonNull
    public String uid;
    /**
     * 商品id
     */
    @NonNull
    public String item_uuid;

    /**
     * 商品
     */
    public Item item;

    /**
     * 数量
     */
    public int quantity;
    /**
     * 商品添加购物车的时间
     */
    public long add_datetime;
    /**
     * 商品在购物车中的选择状态
     */
    public boolean is_selected;

}
