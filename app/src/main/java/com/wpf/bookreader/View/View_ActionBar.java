package com.wpf.bookreader.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.wpf.bookreader.R;


/**
 * Created by 王朋飞 on 11-29-0029.
 *
 */

public class View_ActionBar extends ViewBase {

    public View_ActionBar(Context context) {
        this.context = context;
    }

    @Override
    protected void init() {
        super.init();

        ImageButton action_Back = (ImageButton) getView().findViewById(R.id.action_back);
        action_Back.setOnClickListener(this);
    }

    @Override
    public ViewBase initView(ViewGroup parent) {
        if(mView == null) {
            mView = parent.findViewById(R.id.my_actionbar);
            init();
        }
        return this;
    }

    @Override
    public void getAnimation() {
        mAnimation_Start = AnimationUtils.loadAnimation(context,R.anim.actionbar_down);
        mAnimation_End = AnimationUtils.loadAnimation(context,R.anim.actionbar_up);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                ((AppCompatActivity)context).finish();
                break;
        }
    }
}
