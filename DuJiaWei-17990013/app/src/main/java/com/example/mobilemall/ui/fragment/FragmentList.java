package com.example.mobilemall.ui.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilemall.R;
import com.example.mobilemall.data.database.MallDataBase;
import com.example.mobilemall.databinding.FragmentListBinding;
import com.example.mobilemall.data.entity.Item;
import com.example.mobilemall.ui.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.List;


public class FragmentList extends BaseFragment {
    private FragmentListBinding fragmentListBinding;
    public String keyWords;//搜索关键词


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadItems(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        return fragmentListBinding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private List<Item> items = new ArrayList<>();

    //加载商品列表
    public void loadItems(String keyWords) {
        new Thread(() -> {
            final String key;
            if (this.keyWords != null) {
                key = this.keyWords;
            } else {
                key = keyWords;
            }
            if (key == null) {
                items = MallDataBase.mallDataBase.itemDao().findAll();
            } else {
                items = MallDataBase.mallDataBase.itemDao().findAll(keyWords);
                this.keyWords = null;
            }
            getParentActivity().handler.post(() -> {
                try {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    fragmentListBinding.recyclerView.setLayoutManager(layoutManager);
                    fragmentListBinding.recyclerView.setAdapter(new ItemAdapter(FragmentList.this, fragmentListBinding.recyclerView, items));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }
}