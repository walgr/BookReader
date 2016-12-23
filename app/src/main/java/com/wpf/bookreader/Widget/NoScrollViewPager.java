package com.wpf.bookreader.Widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wpf on 12-18-0018.
 * 不可滑动的ViewPager
 */

public class NoScrollViewPager extends ViewPager {

    private boolean isScrollable;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isScrollable && super.onTouchEvent(ev);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isScrollable && super.onInterceptTouchEvent(ev);

    }

    public NoScrollViewPager setScrollable(boolean isScrollable) {
        this.isScrollable = isScrollable;
        return this;
    }
}
