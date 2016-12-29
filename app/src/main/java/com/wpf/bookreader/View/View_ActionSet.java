package com.wpf.bookreader.View;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.wpf.bookreader.BookInfoActivity;
import com.wpf.bookreader.R;
import com.wpf.bookreader.ReadActivity;
import com.wpf.bookreader.Widget.ReadView;


/**
 * Created by 王朋飞 on 11-29-0029.
 * 设置界面
 */

public class View_ActionSet extends ViewBase {

    private OnCloseListener onCloseListener;
    private ReadView readView;

    public View_ActionSet(ReadView readView) {
        this.readView = readView;
        this.context = readView.getContext();
    }

    @Override
    protected void init() {
        super.init();
        LinearLayout action_Set_Detailed = (LinearLayout) getView().findViewById(R.id.action_set_detailed);
        action_Set_Detailed.setOnClickListener(this);
        LinearLayout action_Set_List = (LinearLayout) getView().findViewById(R.id.action_set_list);
        action_Set_List.setOnClickListener(this);
    }

    @Override
    public ViewBase initView(ViewGroup parent) {
        if(mView == null) {
            mView = parent.findViewById(R.id.my_actionset);
            whiteSpace = parent.findViewById(R.id.white_space);
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
                readView.actionDetailSet.showAction();
                break;
            case R.id.action_set_list:
                ((ReadActivity)context).startActivityForResult(new Intent(context, BookInfoActivity.class),2);
                readView.setBackResult(false);
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
