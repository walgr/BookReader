package com.wpf.bookreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wpf.bookreader.Adapter.MainViewAdapter;
import com.wpf.bookreader.Fragment.BaseFragment;
import com.wpf.bookreader.Fragment.BookShelveFragment;
import com.wpf.bookreader.Fragment.BookShopFragment;
import com.wpf.bookreader.Fragment.UserFragment;
import com.wpf.bookreader.Widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class MainActivity extends AppCompatActivity implements
        OnTabItemSelectListener {

    private NoScrollViewPager viewPager_Home;
    private MainViewAdapter mainViewAdapter;
    private PagerBottomTabLayout pagerBottomTabLayout;
    private List<BaseFragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragments();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((BookShelveFragment)fragmentList.get(0)).doNotify();
    }

    private void init() {
        viewPager_Home = (NoScrollViewPager) findViewById(R.id.viewPager_Home);
        pagerBottomTabLayout = (PagerBottomTabLayout) findViewById(R.id.tab);

        fragmentList.get(0).setTitle(getString(R.string.str_bookshelve));
        fragmentList.get(1).setTitle(getString(R.string.str_bookshop));
        fragmentList.get(2).setTitle(getString(R.string.str_user));

        mainViewAdapter = new MainViewAdapter(getSupportFragmentManager(),fragmentList);
        viewPager_Home.setAdapter(mainViewAdapter);

        pagerBottomTabLayout.builder()
                .addTabItem(R.drawable.ic_home,getString(R.string.str_bookshelve))
                .addTabItem(R.drawable.ic_view_carousel,getString(R.string.str_bookshop))
                .addTabItem(R.drawable.ic_perm_identity,getString(R.string.str_user))
                .build().addTabItemClickListener(this);

    }

    private void getFragments() {
        fragmentList.add(new BookShelveFragment());
        fragmentList.add(new BookShopFragment());
        fragmentList.add(new UserFragment());
    }

    @Override
    public void onSelected(int index, Object tag) {
        viewPager_Home.setCurrentItem(index,false);
    }

    @Override
    public void onRepeatClick(int index, Object tag) {

    }
}
