package com.wpf.bookreader.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.wpf.bookreader.DataBase.ChapterInfo;
import com.wpf.bookreader.DataInfo.PageInfo;
import com.wpf.bookreader.DataInfo.ViewInfo;
import com.wpf.bookreader.Fragment.PageFragment;
import com.wpf.bookreader.Widget.PageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王朋飞 on 12-19-0019.
 *
 */

public class PageListAdapter extends FragmentStatePagerAdapter {

    private List<ChapterInfo> chapterInfoList = new ArrayList<>();
    private ViewInfo viewInfo;
    private PageView.OnClickListener onClickListener;
    private OnInitNextChapter onInitNextChapter;

    public PageListAdapter(FragmentManager fm,ViewInfo viewInfo,PageView.OnClickListener onClickListener) {
        super(fm);
        this.viewInfo = viewInfo;
        this.onClickListener = onClickListener;
    }

    @Override
    public Fragment getItem(int position) {
        int chapterPosition = getChapterPosition(position);
        int pagePosition = getPagePosition(position,chapterPosition);
        if(chapterInfoList.get(chapterPosition).getChapterPageList().isEmpty()) {
            onInitNextChapter.startInit(chapterPosition);
        }
        String pageText = "";
        if(!chapterInfoList.get(chapterPosition).getChapterPageList().isEmpty())
            pageText = chapterInfoList.get(chapterPosition).getChapterPageList().get(pagePosition);
        PageInfo pageInfo = new PageInfo(pagePosition, pageText);
        return PageFragment.newInstance(viewInfo,
                chapterInfoList.get(chapterPosition).getChapterName(), pageInfo)
                .setOnClickListener(onClickListener);
    }

    public int getChapterPosition(int position) {
        int chapterSize = 0;
        for (ChapterInfo chapterInfo : chapterInfoList) {
            if(!chapterInfo.getChapterPageList().isEmpty()) chapterSize += chapterInfo.getChapterPageList().size();
            else chapterSize++;
            if(position < chapterSize)
                return chapterInfo.getPosition();
        }
        return 0;
    }

    private int getPagePosition(int position,int chapterPosition) {
        return position-getPageCount(0,chapterPosition);
    }

    @Override
    public int getCount() {
        return getPageCount(0,chapterInfoList.size());
    }

    public int getPageCount(int start,int end) {
        int count = 0;
        for(int i = start;i<end;++i) {
            if(!chapterInfoList.get(i).getChapterPageList().isEmpty())
                count+=chapterInfoList.get(i).getChapterPageList().size();
            else
                count++;
        }
        return count;
    }

    public PageListAdapter setChapterInfoList(List<ChapterInfo> chapterInfoList) {
        this.chapterInfoList = chapterInfoList;
        notifyDataSetChanged();
        return this;
    }

    public PageListAdapter setViewInfo(ViewInfo viewInfo) {
        this.viewInfo = viewInfo;
        notifyDataSetChanged();
        return this;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    public PageListAdapter setOnInitNextChapter(OnInitNextChapter onInitNextChapter) {
        this.onInitNextChapter = onInitNextChapter;
        return this;
    }

    public interface OnInitNextChapter {
        void startInit(int chapterPosition);
    }
}
