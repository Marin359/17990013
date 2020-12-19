package com.example.mobilemall.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mobilemall.data.entity.ShoppingCart;

import java.util.List;

/**
 * 购物车数据管理
 */
@Dao
public interface ShoppingCartDao {
    /**
     * 购物车新增商品
     */
    @Insert
    void addShoppingCart(ShoppingCart... shoppingCart);

    /**
     * 获取我的购物车列表
     */
    @Query("select * from shopping_carts where uid=:uid order by add_datetime desc")
    List<ShoppingCart> getShoppingCarts(String uid);

    /**
     * 查询商品是否加过购物车
     */
    @Query("select * from shopping_carts where uid=:uid and item_uuid=:item_uuid")
    ShoppingCart findItem(String uid, String item_uuid);

    /**
     * 删除购物车记录
     */
    @Query("delete from shopping_carts where uid=:uid")
    void delete(String uid);

    /**
     * 删除购物车商品
     */
    @Query("delete from shopping_carts where uid=:uid and item_uuid=:item_uuid")
    void delete(String uid, String item_uuid);

    /**
     * 更新购物车记录
     */
    @Update
    void update(ShoppingCart shoppingCart);
}
