package com.example.mobilemall.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mobilemall.R;
import com.example.mobilemall.data.database.MallDataBase;
import com.example.mobilemall.databinding.FragmentIndexBinding;
import com.example.mobilemall.data.entity.Item;
import com.example.mobilemall.ui.adapter.IndexAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 首页
 */
public class FragmentIndex extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private FragmentIndexBinding fragmentIndexBinding;
    private List<Item> itemList = new ArrayList<>();//商品列表

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentIndexBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_index, container, false);
        return fragmentIndexBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentIndexBinding.swipeRefreshLayout.setOnRefreshListener(this);//下拉刷新监听
        initBanner();//初始化banner
        fillItems();
    }

    //banner初始化
    private void initBanner() {
        List<Integer> banners = new ArrayList<>();
        banners.add(R.drawable.banner_1);
        banners.add(R.drawable.banner_2);
        banners.add(R.drawable.banner_3);
        banners.add(R.drawable.banner_4);
        fragmentIndexBinding.banner.setData(banners, null);
        fragmentIndexBinding.banner.loadImage((banner, model, view, position) -> ((ImageView) view).setImageResource(banners.get(position)));
    }

    /*
     * 商品列表
     * */
    public void fillItems() {
        new Thread(() -> {
            itemList = MallDataBase.mallDataBase.itemDao().findAll();//所有商品
            Collections.shuffle(itemList);//乱序
            fragmentIndexBinding.recyclerView.post(() -> {
                try {
                    //实现瀑布流效果
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    fragmentIndexBinding.recyclerView.setLayoutManager(layoutManager);//设置布局管理器
                    //设置适配器
                    fragmentIndexBinding.recyclerView.setAdapter(new IndexAdapter(FragmentIndex.this, fragmentIndexBinding.recyclerView, itemList));
                    fragmentIndexBinding.swipeRefreshLayout.setRefreshing(false);//取消下拉刷新状态
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }


    @Override
    public void onRefresh() {
        fragmentIndexBinding.swipeRefreshLayout.setRefreshing(true);
        fillItems();
    }

}