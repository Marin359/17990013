package com.example.mobilemall.ui.adapter.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilemall.data.entity.Item;
import com.example.mobilemall.databinding.ItemBinding;

public class ItemHolder extends BaseHolder{
    public ItemBinding itemBinding;

    public ItemHolder(@NonNull View itemView, ItemBinding itemBinding) {
        super(itemView);
        this.itemBinding = itemBinding;
    }
    public void setData(Item item, View.OnClickListener onClickListener){
        itemBinding.content.setText(item.content);
        itemBinding.title.setText(item.title);
        itemBinding.price.setText(item.price + "元");
        itemBinding.image.setImageResource(getDrawableId(item.picture));
        //设置缓存
        itemBinding.btnAddShoppingCart.setTag(item);
        itemBinding.btnMore.setTag(item);
        //设置监听事件
        itemBinding.btnAddShoppingCart.setOnClickListener(onClickListener);
        itemBinding.btnMore.setOnClickListener(onClickListener);
        itemBinding.getRoot().setOnClickListener(onClickListener);
    }
}