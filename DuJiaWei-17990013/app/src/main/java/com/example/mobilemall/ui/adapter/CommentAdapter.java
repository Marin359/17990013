package com.example.mobilemall.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.example.mobilemall.R;
import com.example.mobilemall.data.entity.Comment;
import com.example.mobilemall.databinding.CommentItemBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentAdapter extends BaseAdapter {

    private List<Comment> comments = new ArrayList<>();//评价列表
    private final Context context;

    public CommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") CommentItemBinding commentItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.comment_item, null, false);
        commentItemBinding.txtContent.setText(comments.get(position).comment);
        //脱敏
        String uid = comments.get(position).uid;
        uid = uid.substring(0, 4) + "*****" + uid.substring(27);
        commentItemBinding.txtUID.setText(uid);
        commentItemBinding.txtDateTime.setText("发布于 " + simpleDateFormat.format(new Date(comments.get(position).publish_datetime)));
        return commentItemBinding.getRoot();
    }
}
