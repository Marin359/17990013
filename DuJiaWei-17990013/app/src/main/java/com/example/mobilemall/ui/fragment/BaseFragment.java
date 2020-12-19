package com.example.mobilemall.ui.fragment;

import android.app.Fragment;

import com.example.mobilemall.ui.activity.BaseActivity;

public class BaseFragment extends Fragment {

    public BaseActivity getParentActivity() {
        return (BaseActivity) getActivity();
    }
}
