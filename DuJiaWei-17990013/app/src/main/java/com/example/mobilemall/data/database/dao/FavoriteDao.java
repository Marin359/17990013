package com.example.mobilemall.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mobilemall.data.entity.Favorite;

import java.util.List;

/**
 * 收藏数据库管理
 */
@Dao
public interface FavoriteDao {
    /*
     * 新增收藏
     * */
    @Insert
    void insert(Favorite... favorite);

    /*
     * 查找收藏
     * */
    @Query("select * from favorites  where uid=:uid and item_id=:item_id")
    Favorite find(String uid, String item_id);

    /*
     * 取消收藏
     * */
    @Query("delete from favorites where uid=:uid and item_id=:item_id")
    void delete(String uid, String item_id);
}
