package com.example.mobilemall.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mobilemall.util.Constant;

import java.util.UUID;

/**
 * 评论
 */
@Entity(tableName = "comments")
public class Comment {
    @PrimaryKey
    @NonNull
    public String uuid;//主键
    public String uid;//用户唯一标识
    public String item_id;//商品id
    public String comment;//内容
    public long publish_datetime;//发表时间

    public Comment() {
    }

    public Comment(String comment, Item item) {
        this.uuid = UUID.randomUUID().toString();
        this.uid = Constant.account.uid;
        this.item_id = item.uuid;
        this.comment = comment;
        this.publish_datetime = System.currentTimeMillis();
    }
}
