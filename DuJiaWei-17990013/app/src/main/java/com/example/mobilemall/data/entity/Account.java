package com.example.mobilemall.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 账号
 */
@Entity(tableName = "accounts", primaryKeys = {"mobile", "uid"})
public class Account {
    @NonNull
    public String mobile;//手机号
    @NonNull
    public String uid;//用户唯一标识

    public String password;//密码
}
