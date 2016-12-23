package com.wpf.bookreader.View;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.wpf.bookreader.R;


/**
 * Created by 王朋飞 on 11-29-0029.
 * 设置界面
 */

public class View_ActionSet extends ViewBase {

    private OnCloseListener onCloseListener;

    public LinearLayout action_Set_Detailed;
    private ViewBase actionDetailSet;

    public View_ActionSet(Context context) {
        this.context = context;
    }

    @Override
    protected void init() {
        super.init();
        action_Set_Detailed = (LinearLayout) getView().findViewById(R.id.action_set_detailed);
        action_Set_Detailed.setOnClickListener(this);
    }

    @Override
    public ViewBase initView(ViewGroup parent) {
        if(mView == null) {
            mView = parent.findViewById(R.id.my_actionset);
            actionDetailSet = new View_DetailSet(context).initView(parent);
            init();
        }
        return this;
    }

    @Override
    public void getAnimation() {
        mAnimation_Start = AnimationUtils.loadAnimation(context,R.anim.actionset_up);
        mAnimation_End = AnimationUtils.loadAnimation(context,R.anim.actionset_down);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_set_detailed:
                onCloseListener.onOtherActionClose();
                break;
        }
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.onCloseListener = onCloseListener;
    }

    public interface OnCloseListener {
        void onOtherActionClose();
    }

}
