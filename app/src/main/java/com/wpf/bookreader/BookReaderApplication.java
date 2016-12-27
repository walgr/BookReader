package com.wpf.bookreader;

import android.app.Application;
import android.graphics.Color;

import com.socks.library.KLog;
import com.wpf.bookreader.DataBase.greendao.gen.BookInfoDao;
import com.wpf.bookreader.DataBase.greendao.gen.ChapterInfoDao;
import com.wpf.bookreader.DataBase.greendao.gen.DaoMaster;
import com.wpf.bookreader.DataBase.greendao.gen.DaoSession;
import com.wpf.bookreader.DataInfo.ColorInfo;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by 王朋飞 on 12-19-0019.
 *
 */

public class BookReaderApplication extends Application {

    public static OkHttpClient okHttpClient;
    public static BookInfoDao bookInfoDao;
    public static ChapterInfoDao chapterInfoDao;

    private List<ColorInfo> colorInfoList = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(true,"小说阅读");
        initDB();
        initOkHttp();
        initColorList();
    }

    private void initColorList() {
        colorInfoList.add(new ColorInfo(Color.BLACK,Color.GRAY));
        colorInfoList.add(new ColorInfo(Color.BLACK,Color.GREEN));
        colorInfoList.add(new ColorInfo(Color.GRAY,Color.GREEN));
        colorInfoList.add(new ColorInfo(Color.GRAY,Color.YELLOW));
        colorInfoList.add(new ColorInfo(Color.BLACK,Color.YELLOW));
    }

    private void initOkHttp() {
        okHttpClient = new OkHttpClient.Builder()
                //.cache(new Cache(new File(getCacheDir() + "/BookReade"), 1024*1024*100))
                .build();
    }

    private void initDB() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "book.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        chapterInfoDao = daoSession.getChapterInfoDao();
        bookInfoDao = daoSession.getBookInfoDao();
    }

    public List<ColorInfo> getColorInfoList() {
        return colorInfoList;
    }
}
