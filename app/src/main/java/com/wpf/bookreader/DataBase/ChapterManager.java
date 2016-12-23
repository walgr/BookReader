package com.wpf.bookreader.DataBase;

import android.database.sqlite.SQLiteConstraintException;

import com.wpf.bookreader.DataBase.greendao.gen.ChapterInfoDao;

import java.util.List;

import static com.wpf.bookreader.BookReaderApplication.chapterInfoDao;

/**
 * Created by 王朋飞 on 12-14-0014.
 *
 */

public class ChapterManager {

        public static void saveChapter(ChapterInfo chapterInfo) {
        try {
            ChapterInfo chapter = getChapterInfo(chapterInfo.getUrl());
            if(chapter == null)
                chapterInfoDao.insert(chapterInfo);
            else if(!chapter.getChapterPageContent().isEmpty())
                chapterInfoDao.update(chapterInfo);
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
        }
    }

    public static ChapterInfo getChapterInfo(String url) {
        return chapterInfoDao.queryBuilder().where(ChapterInfoDao.Properties.Url.eq(url)).unique();
    }

    public static void saveChapterInfoList(List<ChapterInfo> chapterInfoList) {
        for(ChapterInfo chapterInfo :chapterInfoList) {
            saveChapter(chapterInfo);
        }
    }

    public static List<ChapterInfo> getChapterInfoList() {
        return chapterInfoDao.queryBuilder().list();
    }
}
