package com.example.mobilemall.ui.adapter.holder;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.mobilemall.data.entity.ShoppingCart;
import com.example.mobilemall.databinding.ShoppingcartItemBinding;

public class ShoppingCartItemHolder extends BaseHolder {
    public ShoppingcartItemBinding shoppingcartItemBinding;

    public ShoppingCartItemHolder(@NonNull View itemView, ShoppingcartItemBinding shoppingcartItemBinding) {
        super(itemView);
        this.shoppingcartItemBinding = shoppingcartItemBinding;
    }

    @SuppressLint("SetTextI18n")
    public void setData(ShoppingCart shoppingCart, View.OnClickListener onClickListener) {
        //设置监听事件
        shoppingcartItemBinding.btnAdd.setOnClickListener(onClickListener);
        shoppingcartItemBinding.btnRemove.setOnClickListener(onClickListener);
        shoppingcartItemBinding.chkSelect.setOnClickListener(onClickListener);
        shoppingcartItemBinding.chkSelect.setChecked(shoppingCart.is_selected);
        shoppingcartItemBinding.getRoot().setOnClickListener(onClickListener);
        //设置缓存
        shoppingcartItemBinding.txtQuantity.setTag(shoppingCart);
        shoppingcartItemBinding.btnAdd.setTag(shoppingcartItemBinding);
        shoppingcartItemBinding.chkSelect.setTag(shoppingcartItemBinding);
        shoppingcartItemBinding.btnRemove.setTag(shoppingcartItemBinding);
        //填充内容
        shoppingcartItemBinding.title.setText(shoppingCart.item.title);
        shoppingcartItemBinding.price.setText(shoppingCart.item.price + "元");
        shoppingcartItemBinding.image.setImageResource(getDrawableId(shoppingCart.item.picture));
        shoppingcartItemBinding.txtQuantity.setText(shoppingCart.quantity + "");
    }

}