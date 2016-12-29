package com.wpf.bookreader.Utils;

import android.content.Context;

import com.wpf.bookreader.DataBase.ChapterInfo;
import com.wpf.bookreader.DataInfo.ViewInfo;

import java.util.List;

/**
 * Created by 王朋飞 on 12-29-0029.
 *
 */

public class ChapterInitManager {

    public void init(final Context context, final ViewInfo viewInfo, final ChapterInfo chapterInfo) {
        new PageListManager().getPageText(context, chapterInfo.getChapterPageContent(),
                viewInfo, new PageListManager.OnTextFinish() {
                    @Override
                    public void onSuccess(List<String> pageTextList) {
                        chapterInfo.setChapterPageList(pageTextList);
                        //pageListAdapter.notifyDataSetChanged();
                        //pageList.setCurrentItem(pageListAdapter.getPageCount(0,curChapterPosition) + curPagePosition,false);
//                        pageList.setScrollable(true);
                    }
                });
    }

}
