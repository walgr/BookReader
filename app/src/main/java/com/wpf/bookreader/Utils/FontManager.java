package com.wpf.bookreader.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 王朋飞 on 12-1-0001.
 * 保存信息
 */

public class FontManager {

    public static void saveFontName(Context context, String fontName) {
        SharedPreferences.Editor editor = context.getSharedPreferences("FontInfo",Context.MODE_PRIVATE).edit();
        editor.putString("fontName",fontName);
        editor.apply();
    }

    public static String getFontName(Context context) {
        SharedPreferences sp = context.getSharedPreferences("FontInfo",Context.MODE_PRIVATE);
        return sp.getString("fontName","系统默认");
    }
}
