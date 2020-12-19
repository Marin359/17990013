package com.example.mobilemall.ui.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilemall.R;
import com.example.mobilemall.data.database.MallDataBase;
import com.example.mobilemall.data.entity.Item;
import com.example.mobilemall.data.entity.ShoppingCart;

import com.example.mobilemall.databinding.ShoppingcartItemBinding;
import com.example.mobilemall.ui.activity.ItemDetailActivity;
import com.example.mobilemall.ui.adapter.holder.ShoppingCartItemHolder;
import com.example.mobilemall.ui.fragment.FragmentShoppingCart;
import com.example.mobilemall.data.tools.ShoppingCartUtil;
import com.example.mobilemall.util.Constant;

import java.util.List;

/**
 * 购物车列表适配器
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartItemHolder> implements View.OnClickListener {

    private final FragmentShoppingCart fragment;
    private final List<ShoppingCart> shoppingCarts;
    private final RecyclerView recyclerView;

    public ShoppingCartAdapter(FragmentShoppingCart fragment, RecyclerView recyclerView, List<ShoppingCart> shoppingCarts) {
        this.fragment = fragment;
        this.shoppingCarts = shoppingCarts;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ShoppingCartItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ShoppingcartItemBinding shoppingcartItemBinding = DataBindingUtil.inflate(LayoutInflater.from(fragment.getParentActivity()), R.layout.shoppingcart_item, parent, false);
        return new ShoppingCartItemHolder(shoppingcartItemBinding.getRoot(), shoppingcartItemBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ShoppingCartItemHolder holder, int position) {
        //设置监听事件
        holder.setData(shoppingCarts.get(position), this);
    }

    @Override
    public int getItemCount() {
        return shoppingCarts.size();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd: {
                //添加
                ShoppingcartItemBinding shoppingcartItemBinding = (ShoppingcartItemBinding) v.getTag();
                ShoppingCart shoppingCart = (ShoppingCart) shoppingcartItemBinding.txtQuantity.getTag();
                if (shoppingCart.quantity < 50) {
                    new Thread(() -> {
                        //添加购物车
                        ShoppingCartUtil.addShoppingCart(shoppingCart.item);
                        //刷新购物车
                        fragment.getParentActivity().handler.post(fragment::loadShoppingCarts);
                    }).start();
                }
            }
            break;
            case R.id.btnRemove: {
                //减少
                ShoppingcartItemBinding shoppingcartItemBinding = (ShoppingcartItemBinding) v.getTag();
                ShoppingCart shoppingCart = (ShoppingCart) shoppingcartItemBinding.txtQuantity.getTag();
                if (shoppingCart.quantity != 1) {
                    new Thread(() -> {
                        //添加购物车
                        ShoppingCartUtil.setShoppingCart(shoppingCart.item, shoppingCart.quantity - 1);
                        //刷新购物车
                        fragment.getParentActivity().handler.post(fragment::loadShoppingCarts);
                    }).start();
                }
            }
            break;
            case R.id.chkSelect: {
                //选择
                ShoppingcartItemBinding shoppingcartItemBinding = (ShoppingcartItemBinding) v.getTag();
                ShoppingCart shoppingCart = (ShoppingCart) shoppingcartItemBinding.txtQuantity.getTag();
                shoppingCart.is_selected = ((CheckBox) v).isChecked();
                new Thread(() -> {
                    MallDataBase.mallDataBase.shoppingCartDao().update(shoppingCart);
                    fragment.getParentActivity().handler.post(fragment::loadShoppingCarts);//重新加载购物车
                }).start();
            }
            break;
            default: {
                //行点击事件
                int position = recyclerView.getChildAdapterPosition(v);
                //上下文菜单
                Item item = shoppingCarts.get(position).item;
                String[] items = new String[]{"查看详情", "移除"};
                new AlertDialog.Builder(fragment.getParentActivity()).setTitle("操作").setItems(items, (dialog, which) -> {
                    if (which == 0) {
                        //查看
                        Intent intent = new Intent(fragment.getParentActivity(), ItemDetailActivity.class);
                        intent.putExtra("uuid", item.uuid);
                        fragment.startActivity(intent);
                    } else {
                        new Thread(() -> {
                            //移除商品
                            MallDataBase.mallDataBase.shoppingCartDao().delete(Constant.account.uid, item.uuid);
                            fragment.getParentActivity().toast("移除成功");
                            fragment.getParentActivity().handler.post(fragment::loadShoppingCarts);
                        }).start();
                    }
                }).show();
            }
        }
    }
}