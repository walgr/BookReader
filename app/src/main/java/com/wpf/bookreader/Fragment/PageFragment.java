package com.wpf.bookreader.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wpf.bookreader.DataInfo.PageInfo;
import com.wpf.bookreader.DataInfo.ViewInfo;
import com.wpf.bookreader.R;
import com.wpf.bookreader.Widget.PageView;

public class PageFragment extends Fragment {

    public static String flag_ViewInfo = "flag_ViewInfo";
    public static String flag_ChapterName = "flag_ChapterName";
    public static String flag_PageInfo = "flag_PageInfo";

    private ViewInfo viewInfo = new ViewInfo();
    private String chapterName;
    private PageInfo pageInfo;

    private PageView page;
    private LinearLayout noText;
    private TextView textView_ChapterName;
    private PageView.OnClickListener onClickListener;

    public static PageFragment newInstance(ViewInfo viewInfo, String chapterName, PageInfo pageInfo) {
        PageFragment pageFragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(flag_ViewInfo,viewInfo);
        bundle.putString(flag_ChapterName,chapterName);
        bundle.putParcelable(flag_PageInfo,pageInfo);
        pageFragment.setArguments(bundle);
        return pageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null) {
            viewInfo = bundle.getParcelable(flag_ViewInfo);
            chapterName = bundle.getString(flag_ChapterName);
            pageInfo = bundle.getParcelable(flag_PageInfo);
        }

        page = (PageView) view.findViewById(R.id.page);
        noText = (LinearLayout) view.findViewById(R.id.noText);
        textView_ChapterName = (TextView) view.findViewById(R.id.textView_ChapterName);
        textView_ChapterName.setText(chapterName);
        if(!pageInfo.getText().isEmpty()) {
            noText.setVisibility(View.GONE);
            page.setOnClickListener(onClickListener);
            page.setViewInfo(viewInfo);
            page.setTitle(chapterName);
            page.setText(pageInfo.getText());
            page.invalidate();
        }
    }

    public PageFragment setOnClickListener(PageView.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }
}
