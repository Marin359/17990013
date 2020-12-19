package com.example.mobilemall.ui.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

import com.example.mobilemall.R;
import com.example.mobilemall.data.database.MallDataBase;
import com.example.mobilemall.data.entity.Item;
import com.example.mobilemall.databinding.ActivityFirstBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 引导页面
 */
public class FirstActivity extends BaseActivity implements View.OnClickListener {
    private ActivityFirstBinding activityFirstBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFirstBinding = DataBindingUtil.setContentView(this, R.layout.activity_first);
        initRandomItems();
        if (isFirst()) {
            //如果进过引导页了,下次就不展示了
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            initBanner();
        }
    }

    /*
     * 按钮单击事件
     * */
    @Override
    public void onClick(View v) {
        saveHistory();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private boolean isFirst() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("not_fist", false);
    }

    /*
     * 引导页的轮播图片
     * */
    private void initBanner() {
        List<Integer> banners = new ArrayList<>();
        banners.add(R.drawable.fist1);
        banners.add(R.drawable.first2);
        banners.add(R.drawable.first4);
        banners.add(R.drawable.first3);
        activityFirstBinding.banner.setData(banners, null);
        activityFirstBinding.banner.loadImage((banner, model, view, position) -> ((ImageView) view).setImageResource(banners.get(position)));

    }

    /*
     * 保存记录,下次启动不再显示引导页面
     * */
    private void saveHistory() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("not_fist", true).apply();
    }

    /*
     * 初始化随机商品
     * */
    private void initRandomItems() {
        new Thread(() -> {
            if (MallDataBase.mallDataBase.itemDao().findAll().size() > 0) {
                return;
            }
            String title = "测试商品，图片来自网络";
            String content = "这是商品的描述内容\n展示描述的内容\n品牌：测试\n型号：Test\n进本信息：测试商品\n保修：3年";
            for (int i = 0; i < 35; i++) {
                Item item = new Item();
                item.uuid = UUID.randomUUID().toString();
                item.add_datetime = System.currentTimeMillis();
                item.content = content;
                item.title = i + title;
                item.picture = "item_demo" + (i + 1);
                item.price = new Random(1000).nextFloat() * 1000;
                MallDataBase.mallDataBase.itemDao().addItem(item);
            }
        }).start();

    }
}