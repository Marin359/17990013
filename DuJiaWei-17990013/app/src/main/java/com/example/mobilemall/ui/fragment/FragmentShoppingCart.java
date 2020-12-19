package com.example.mobilemall.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mobilemall.R;
import com.example.mobilemall.data.database.MallDataBase;
import com.example.mobilemall.data.entity.ShoppingCart;
import com.example.mobilemall.databinding.FragmentCartBinding;
import com.example.mobilemall.ui.adapter.ShoppingCartAdapter;
import com.example.mobilemall.util.Constant;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/*
 * 购物车页
 * */
public class FragmentShoppingCart extends BaseFragment implements View.OnClickListener {
    private FragmentCartBinding fragmentCartBinding;
    private List<ShoppingCart> shoppingCarts = new ArrayList<>();//购物车列表
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");//保留小数点后两位

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        return fragmentCartBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //全选按钮监听事件
        fragmentCartBinding.chkSelectAll.setOnClickListener(v -> new Thread(() -> {
            for (ShoppingCart shoppingCart : shoppingCarts) {
                shoppingCart.is_selected = fragmentCartBinding.chkSelectAll.isChecked();
                MallDataBase.mallDataBase.shoppingCartDao().update(shoppingCart);
            }
            //重新加载购物车列表
            getParentActivity().handler.post(this::loadShoppingCarts);
        }).start());
        fragmentCartBinding.btnDelete.setOnClickListener(this);//删除
        loadShoppingCarts();
    }

    /**
     * 加载购物车列表
     */
    public void loadShoppingCarts() {
        new Thread(() -> {
            shoppingCarts = MallDataBase.mallDataBase.shoppingCartDao().getShoppingCarts(Constant.account.uid);
            getParentActivity().handler.post(() -> {
                try {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    fragmentCartBinding.recyclerView.setLayoutManager(layoutManager);
                    fragmentCartBinding.recyclerView
                            .setAdapter(new ShoppingCartAdapter(FragmentShoppingCart.this, fragmentCartBinding.recyclerView, shoppingCarts));
                    setTotalFee();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }

    /**
     * 计算购物车选中商品总额
     */
    @SuppressLint("SetTextI18n")
    private void setTotalFee() {
        int totalKind = 0;//总商品种类
        int total = 0;//总商品数量
        BigDecimal sum = new BigDecimal(0);//总和
        for (ShoppingCart shoppingCart : shoppingCarts) {
            if (shoppingCart.is_selected) {
                sum = sum.add(new BigDecimal(shoppingCart.quantity).multiply(new BigDecimal(shoppingCart.item.price)));
                totalKind++;
                total += shoppingCart.quantity;
            }
        }
        //选中商品总价
        fragmentCartBinding.txtFee.setText(total + "件商品，共计" + decimalFormat.format(sum.floatValue()) + "元");
        //全选按钮选中状态
        fragmentCartBinding.chkSelectAll.setChecked(totalKind != 0 && totalKind == shoppingCarts.size());
        //全选按钮选中可见状态
        fragmentCartBinding.chkSelectAll.setVisibility(shoppingCarts.size() > 0 ? View.VISIBLE : View.GONE);
        //删除按钮可见
        fragmentCartBinding.btnDelete.setVisibility(totalKind > 0 ? View.VISIBLE : View.GONE);
        //结算按钮状态
        fragmentCartBinding.btnConfirm.setEnabled(totalKind > 0);
    }


    @Override
    public void onClick(View v) {
        //批量移除商品
        if (v.getId() == R.id.btnDelete) {
            new Thread(() -> {
                for (ShoppingCart shoppingCart : shoppingCarts) {
                    if (shoppingCart.is_selected) {
                        MallDataBase.mallDataBase.shoppingCartDao().delete(Constant.account.uid, shoppingCart.item_uuid);
                    }
                }
                //重新加载购物车
                getParentActivity().handler.post(this::loadShoppingCarts);
            }).start();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadShoppingCarts();
    }
}