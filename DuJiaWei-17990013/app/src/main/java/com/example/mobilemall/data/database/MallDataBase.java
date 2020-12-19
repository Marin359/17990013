package com.example.mobilemall.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mobilemall.data.database.dao.CommentDao;
import com.example.mobilemall.data.entity.Comment;
import com.example.mobilemall.data.entity.ShoppingCart;
import com.example.mobilemall.data.entity.Favorite;
import com.example.mobilemall.data.entity.Item;
import com.example.mobilemall.data.entity.Account;
import com.example.mobilemall.data.database.dao.ShoppingCartDao;
import com.example.mobilemall.data.database.dao.FavoriteDao;
import com.example.mobilemall.data.database.dao.ItemDao;
import com.example.mobilemall.data.database.dao.AccountDao;

/**
 * 数据库操作类
 */
@Database(entities = {Item.class, Account.class, ShoppingCart.class, Favorite.class, Comment.class}, version = 1)
public abstract class MallDataBase extends RoomDatabase {
    public abstract ItemDao itemDao();

    public abstract AccountDao accountDao();

    public abstract CommentDao commentDao();

    public abstract ShoppingCartDao shoppingCartDao();

    public abstract FavoriteDao favoriteDao();

    public static MallDataBase mallDataBase;//数据库操作对象
}
