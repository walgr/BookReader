package com.wpf.bookreader.Fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 王朋飞 on 7-12-0012.
 * 基础Fragment
 */

public abstract class BaseFragment extends Fragment {

    private String title = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public View view;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,int resource) {
        if(view == null) {
            view = inflater.inflate(resource, container, false);
            initView();
        }
        return view;
    }

    public abstract void initView();
}
