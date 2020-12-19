package com.example.mobilemall.ui.adapter.holder;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilemall.data.entity.Item;
import com.example.mobilemall.databinding.IndexItemBinding;

import java.util.Random;

public class IndexHolder extends BaseHolder{
    public IndexItemBinding indexItemBinding;


    public IndexHolder(@NonNull View itemView, IndexItemBinding recommendGoodItemBinding) {
        super(itemView);
        this.indexItemBinding = recommendGoodItemBinding;
        ViewGroup.LayoutParams params = recommendGoodItemBinding.getRoot().getLayoutParams();
        params.height = params.height + new Random().nextInt(321);
        recommendGoodItemBinding.getRoot().setLayoutParams(params);
    }

    public void setData(Item item, View.OnClickListener onClickListener) {
        //填充内容
        indexItemBinding.content.setText(item.content);
        indexItemBinding.title.setText(item.title);
        indexItemBinding.price.setText(item.price + "元");
        indexItemBinding.image.setImageResource(getDrawableId(item.picture));
        //设置缓存
        indexItemBinding.btnAddShoppingCart.setTag(item);
        //设置监听事件
        indexItemBinding.btnAddShoppingCart.setOnClickListener(onClickListener);
        indexItemBinding.getRoot().setOnClickListener(onClickListener);
    }
}
