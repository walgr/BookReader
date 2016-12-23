package com.wpf.bookreader.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.wpf.bookreader.DataBase.ChapterInfo;

/**
 * Created by 王朋飞 on 12-1-0001.
 * 保存信息
 */

public class SaveInfo {

    public static void saveFontName(Context context, String fontName) {
        SharedPreferences.Editor editor = context.getSharedPreferences("FontInfo",Context.MODE_PRIVATE).edit();
        editor.putString("fontName",fontName);
        editor.apply();
    }

    public static String getFontName(Context context) {
        SharedPreferences sp = context.getSharedPreferences("FontInfo",Context.MODE_PRIVATE);
        return sp.getString("fontName","系统默认");
    }

    public static void saveChapterHistory(Context context, ChapterInfo chapterInfo) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ChapterHistory",Context.MODE_PRIVATE).edit();
        editor.putString("chapterHistory",new Gson().toJson(chapterInfo));
        editor.apply();
    }

    public static ChapterInfo getChapterHistory(Context context) {
        SharedPreferences sp = context.getSharedPreferences("ChapterHistory",Context.MODE_PRIVATE);
        String str = sp.getString("chapterHistory","");
        if(str.isEmpty()) return null;
        return new Gson().fromJson(str,ChapterInfo.class);
    }
}
