package com.example.mobilemall.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mobilemall.data.entity.Comment;

import java.util.List;

@Dao
public interface CommentDao {
    /*
     * 发布评论
     * */
    @Insert
    void insert(Comment comment);

    /*
     *  获取商品评论
     * */
    @Query("select * from comments where item_id=:itemID order by publish_datetime desc")
    List<Comment> getCommentByItemId(String itemID);
}
