package com.example.mobilemall.ui.activity;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mobilemall.R;
import com.example.mobilemall.data.database.MallDataBase;
import com.example.mobilemall.data.entity.Comment;
import com.example.mobilemall.data.entity.Favorite;
import com.example.mobilemall.data.entity.Item;
import com.example.mobilemall.data.tools.ShoppingCartUtil;
import com.example.mobilemall.databinding.ActivityItemDetailBinding;
import com.example.mobilemall.ui.adapter.CommentAdapter;
import com.example.mobilemall.ui.adapter.holder.BaseHolder;
import com.example.mobilemall.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {
    private ActivityItemDetailBinding activityItemDetailBinding;
    private Item item;//当前商品
    private Toolbar toolbar;//工具栏
    private boolean isFavorite = false;//是否收藏状态
    private List<Comment> comments = new ArrayList<>();//评价列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityItemDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_item_detail);
        toolbar = activityItemDetailBinding.titlebar;
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.menu_item_detail);//溢出菜单
        toolbar.setOnMenuItemClickListener(this);
        String uuid = getIntent().getStringExtra("uuid");
        new Thread(() -> {
            //查询商品
            item = MallDataBase.mallDataBase.itemDao().find(uuid);
            boolean isFavorite = isFavorite(Constant.account.uid, item.uuid);
            if (item != null) {
                //填充页面
                loadComments();//获取评价
                handler.post(() -> {
                    initView();
                    activityItemDetailBinding.chkFavorite.setChecked(isFavorite);
                });
            } else {
                finish();
                toast("商品不存在");
            }
        }).start();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        toolbar.setTitle(item.title);
        activityItemDetailBinding.content.setText(item.content);
        activityItemDetailBinding.price.setText("￥ " + item.price);
        activityItemDetailBinding.image.setImageResource(BaseHolder.getDrawableId(item.picture));
        activityItemDetailBinding.title.setText(item.title);
        initFavoriteState();
    }

    /**
     * 初始化收藏状态
     */
    private void initFavoriteState() {
        toolbar.getMenu().getItem(1).setTitle(isFavorite ? "取消收藏" : "收藏");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddShoppingCart: {
                new Thread(() -> {
                    //添加购物车
                    ShoppingCartUtil.addShoppingCart(item);
                    toast("添加购物车成功");
                }).start();
            }
            break;
            case R.id.btnBack: {
                //返回
                finish();
            }
            break;
            case R.id.chkFavorite: {
                //收藏/取消收藏
                new Thread(() -> {
                    try {
                        if (isFavorite) {
                            delete(Constant.account.uid, item.uuid);
                        } else {
                            //新增收藏
                            Favorite favorite = new Favorite();
                            favorite.item = item;
                            favorite.time = System.currentTimeMillis();
                            favorite.item_id = item.uuid;
                            favorite.uid = Constant.account.uid;
                            add(favorite);
                        }
                        isFavorite = isFavorite(Constant.account.uid, item.uuid);
                        toast(isFavorite ? "收藏成功" : "取消收藏成功");
                        handler.post(this::initFavoriteState);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            break;
            case R.id.btnPublish: {
                //发布评价
                String content = activityItemDetailBinding.etComment.getText().toString();
                if (content.length() > 0) {
                    new Thread(() -> {
                        MallDataBase.mallDataBase.commentDao().insert(new Comment(content, item));
                        handler.post(this::loadComments);
                        toast("发布成功");
                    }).start();
                }
            }
        }
    }

    /*
     * 加载评论
     * */
    private void loadComments() {
        new Thread(() -> {
            comments = MallDataBase.mallDataBase.commentDao().getCommentByItemId(item.uuid);
            handler.post(() -> {
                activityItemDetailBinding.listView.setAdapter(new CommentAdapter(this, comments));
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            handler.post(() -> {
                int totalHeight = 0;
                for (int i = 0; i < comments.size(); i++) {
                    View listItem = activityItemDetailBinding.listView.getAdapter().getView(i, null, activityItemDetailBinding.listView);
                    listItem.measure(0, 0);
                    totalHeight += listItem.getMeasuredHeight();
                }
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) activityItemDetailBinding.listView.getLayoutParams();
                layoutParams.height = totalHeight + ( activityItemDetailBinding.listView.getDividerHeight() * (comments.size() - 1));
                activityItemDetailBinding.listView.setLayoutParams(layoutParams);
            });
        }).start();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_shoppingcart: {
                activityItemDetailBinding.btnAddShoppingCart.performClick();
            }
            break;
            case R.id.menu_back: {
                activityItemDetailBinding.btnBack.performClick();
            }
            break;
            case R.id.menu_favorite: {
                activityItemDetailBinding.chkFavorite.performClick();
            }
            break;
        }
        return false;
    }

    /**
     * 查询商品收藏状态
     */
    private boolean isFavorite(String username, String good_id) {
        return MallDataBase.mallDataBase.favoriteDao().find(username, good_id) != null;
    }

    /**
     * 收藏
     */
    private void add(Favorite favorite) {
        MallDataBase.mallDataBase.favoriteDao().insert(favorite);
    }

    /**
     * 取消收藏
     */
    private void delete(String username, String good_id) {
        MallDataBase.mallDataBase.favoriteDao().delete(username, good_id);
    }
}