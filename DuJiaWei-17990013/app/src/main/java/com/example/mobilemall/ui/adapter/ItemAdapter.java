package com.example.mobilemall.ui.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilemall.R;
import com.example.mobilemall.data.entity.Item;
import com.example.mobilemall.databinding.ItemBinding;
import com.example.mobilemall.ui.activity.ItemDetailActivity;
import com.example.mobilemall.ui.adapter.holder.ItemHolder;
import com.example.mobilemall.ui.fragment.FragmentList;
import com.example.mobilemall.data.tools.ShoppingCartUtil;

import java.util.List;
/*
* 商品列表适配器
* */
public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> implements View.OnClickListener {

    private final FragmentList fragment;
    private final List<Item> list;
    private final RecyclerView recyclerView;

    public ItemAdapter(FragmentList fragment, RecyclerView recyclerView, List<Item> list) {
        this.fragment = fragment;
        this.list = list;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBinding goodItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(fragment.getParentActivity()), R.layout.item, parent, false);
        return new ItemHolder(goodItemBinding.getRoot(), goodItemBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.setData(list.get(position), this);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddShoppingCart) {
            Item item = (Item) v.getTag();
            new Thread(() -> {
                //添加购物车
                ShoppingCartUtil.addShoppingCart(item);
                fragment.getParentActivity().toast("添加购物车成功");
            }).start();
        } else if (v.getId() == R.id.btnMore) {
            //更多
            Item item = (Item) v.getTag();
            String[] items = new String[]{"查看", "加入购物车"};
            new AlertDialog.Builder(fragment.getParentActivity()).setTitle("操作").setItems(items, (dialog, which) -> {
                if (which == 0) {
                    //查看
                    Intent intent = new Intent(fragment.getParentActivity(), ItemDetailActivity.class);
                    intent.putExtra("uuid", item.uuid);
                    fragment.startActivity(intent);
                } else {
                    new Thread(() -> {
                        //添加购物车
                        ShoppingCartUtil.addShoppingCart(item);
                        fragment.getParentActivity().toast("添加购物车成功");
                    }).start();
                }

            }).show();
        } else {
            int position = recyclerView.getChildAdapterPosition(v);
            //查看
            Intent intent = new Intent(fragment.getParentActivity(), ItemDetailActivity.class);
            intent.putExtra("uuid", list.get(position).uuid);
            fragment.startActivity(intent);
        }
    }
}
