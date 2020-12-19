package com.example.mobilemall.ui.activity;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;

import com.example.mobilemall.R;
import com.example.mobilemall.databinding.ActivityMainBinding;
import com.example.mobilemall.ui.fragment.FragmentShoppingCart;
import com.example.mobilemall.ui.fragment.FragmentList;
import com.example.mobilemall.ui.fragment.FragmentIndex;

public class MainActivity extends BaseActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener {
    private ActivityMainBinding activityMainBinding;
    private int currentIndex = 0;//当前页
    private FragmentManager fragmentManager;//fragment管理器
    private FragmentList fragmentList;//商品列表页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //fragment管理
        fragmentManager = getFragmentManager();
        activityMainBinding.titlebar.setTitleTextColor(Color.WHITE);
        //设置标题文字
        activityMainBinding.titlebar.setTitle("推荐");
        //加载溢出菜单
        activityMainBinding.titlebar.inflateMenu(R.menu.menu_main);
        //设置溢出菜单事件
        activityMainBinding.titlebar.setOnMenuItemClickListener(this);
        //显示主页
        showIndex();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_signout: {
                //注销,清除保存的用户信息
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                sharedPreferences.edit().putString("user_info", null).apply();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
            break;
            case R.id.menu_exit: {
                //关闭本程序
                finish();
            }
        }
        return false;
    }

    /*
     * 根据关键词搜索商品
     * */
    private void loadItemList(String keyWords) {
        if (currentIndex == 1) {
            //当前是商品列表页
            fragmentList.loadItems(keyWords);
        } else {
            //不是商品列表页，加载商品列表页后再搜索
            loadList(keyWords);
        }
    }

    /**
     * 显示首页
     */
    private void showIndex() {
        currentIndex = 0;
        showSearch();
        activityMainBinding.titlebar.setTitle("推荐商品");
        FragmentIndex recommendFragment = new FragmentIndex();
        fragmentManager.beginTransaction().replace(R.id.fragment, recommendFragment).commit();
        activityMainBinding.rbIndex.setChecked(true);
        activityMainBinding.rbList.setChecked(false);
        activityMainBinding.rbShoppingCart.setChecked(false);
    }

    /**
     * 显示商品列表页面
     */
    private void loadList(String keyWords) {
        showSearch();
        currentIndex = 1;
        activityMainBinding.titlebar.setTitle("商品列表");
        fragmentList = new FragmentList();
        fragmentList.keyWords = keyWords;
        switchFragment(fragmentList);
        activityMainBinding.rbIndex.setChecked(false);
        activityMainBinding.rbList.setChecked(true);
        activityMainBinding.rbShoppingCart.setChecked(false);
    }

    /**
     * 显示购物车
     */
    private void showCart() {
        activityMainBinding.searchParent.setVisibility(View.GONE);
        currentIndex = 2;
        activityMainBinding.titlebar.setTitle("购物车");
        switchFragment(new FragmentShoppingCart());
        activityMainBinding.rbIndex.setChecked(false);
        activityMainBinding.rbList.setChecked(false);
        activityMainBinding.rbShoppingCart.setChecked(true);
    }

    private void switchFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    /*
     * 显示搜索栏
     * */
    private void showSearch() {
        activityMainBinding.searchParent.setVisibility(View.VISIBLE);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearchItems: {
                //搜索按钮
                String keyWords = activityMainBinding.etText.getText().toString();
                loadItemList(keyWords);
            }
            break;
            case R.id.btnSearchReset: {
                //取消搜索
                activityMainBinding.etText.setText("");
                if (currentIndex == 1) {
                    loadItemList(null);
                }
            }
            break;
            case R.id.rbIndex: {
                //首页
                showIndex();
            }
            break;
            case R.id.rbList: {
                //商品列表
                loadList(null);

            }
            break;
            case R.id.rbShoppingCart: {
                //购物车
                showCart();
            }
            break;
        }
    }
}