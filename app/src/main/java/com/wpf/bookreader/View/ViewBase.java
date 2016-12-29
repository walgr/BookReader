package com.wpf.bookreader.View;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

/**
 * Created by 王朋飞 on 11-29-0029.
 * 基础View
 */

public abstract class ViewBase implements
        View.OnClickListener {

    public boolean isShow;
    Context context;
    View mView,whiteSpace;
    Animation mAnimation_Start;
    Animation mAnimation_End;

    protected void init() {
        getAnimation();
        getView().setOnClickListener(this);
    }

    public View getView() {
        return mView;
    }

    public abstract ViewBase initView(ViewGroup parent);

    public void showAction() {
        if(!isShow) {
            getView().setVisibility(View.VISIBLE);
            if(whiteSpace != null) whiteSpace.setVisibility(View.VISIBLE);
            getView().startAnimation(mAnimation_Start);
            isShow = true;
        }
    }

    public void notShowAction() {
        if(isShow) {
            getView().startAnimation(mAnimation_End);
            if(whiteSpace != null) whiteSpace.setVisibility(View.GONE);
            getView().setVisibility(View.GONE);
            isShow = false;
        }
    }

    public abstract void getAnimation();
}
