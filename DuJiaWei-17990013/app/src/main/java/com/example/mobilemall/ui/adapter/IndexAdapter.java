package com.example.mobilemall.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilemall.R;
import com.example.mobilemall.data.entity.Item;
import com.example.mobilemall.databinding.IndexItemBinding;
import com.example.mobilemall.ui.activity.ItemDetailActivity;
import com.example.mobilemall.ui.adapter.holder.IndexHolder;
import com.example.mobilemall.ui.fragment.FragmentIndex;
import com.example.mobilemall.data.tools.ShoppingCartUtil;

import java.util.List;

/**
 * 主页商品列表适配器
 */
public class IndexAdapter extends RecyclerView.Adapter<IndexHolder> implements View.OnClickListener {
    private final FragmentIndex fragment;
    private final List<Item> list;
    private final RecyclerView recyclerView;

    public IndexAdapter(FragmentIndex fragment, RecyclerView recyclerView, List<Item> list) {
        this.fragment = fragment;
        this.list = list;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public IndexHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IndexItemBinding goodItemBinding = DataBindingUtil.inflate(LayoutInflater.from(fragment.getParentActivity()), R.layout.index_item, parent, false);
        return new IndexHolder(goodItemBinding.getRoot(), goodItemBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull IndexHolder holder, int position) {
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
        } else {
            int position = recyclerView.getChildAdapterPosition(v);
            //查看商品详情
            Intent intent = new Intent(fragment.getParentActivity(), ItemDetailActivity.class);
            intent.putExtra("uuid", list.get(position).uuid);
            fragment.startActivity(intent);
        }

    }
}