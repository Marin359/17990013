package com.example.mobilemall.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mobilemall.data.entity.Item;

import java.util.List;

@Dao
public interface ItemDao {
    //新增商品
    @Insert
    void addItem(Item... items);

    //查询所有商品，按时间倒序排列
    @Query("select * from items order by add_datetime desc")
    List<Item> findAll();

    //查询指定商品
    @Query("select * from items where uuid=:uuid")
    Item find(String uuid);

    //根据关键词查询商品，按时间倒序排列
    @Query("select * from items where title like '%'|| :keywords|| '%' or content like '%'|| :keywords|| '%'")
    List<Item> findAll(String keywords);

    //删除所有商品
    @Query("delete from items")
    void deleteAll();
}
