package com.wpf.bookreader.Adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wpf.bookreader.Fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王朋飞 on 7-12-0012.
 * 主视图适配器
 */

public class MainViewAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private FragmentManager fm;
    public MainViewAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.fm = fm;
        this.fragmentList = fragmentList;
    }

    @Override
    public BaseFragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
