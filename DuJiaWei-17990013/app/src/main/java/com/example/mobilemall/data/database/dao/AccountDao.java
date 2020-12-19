package com.example.mobilemall.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mobilemall.data.entity.Account;

import java.util.List;

@Dao
public interface AccountDao {
    /**
     * 检查手机号是否可用
     */
    @Query("select * from accounts where mobile=:mobile")
    List<Account> checkMobile(String mobile);

    /**
     * 注册
     */
    @Insert
    void register(Account account);

    /**
     * 登录验证
     */
    @Query("select * from accounts where mobile=:mobile and password=:passWord")
    Account login(String mobile, String passWord);
}
