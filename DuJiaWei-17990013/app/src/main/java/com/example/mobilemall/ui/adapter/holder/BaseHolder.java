package com.example.mobilemall.ui.adapter.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilemall.MallApplication;

import java.lang.reflect.Field;

public class BaseHolder extends RecyclerView.ViewHolder {

    public BaseHolder(@NonNull View itemView) {
        super(itemView);
    }

    public static int getDrawableId(String imageName) {
        try {
            Field field = Class.forName(MallApplication.mallApplication.getPackageName() + ".R$drawable").getField(imageName);
            return field.getInt(field);
        } catch (Exception e) {
            return -1;
        }
    }


}
