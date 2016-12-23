package com.wpf.bookreader.Utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.TextPaint;
import android.util.TypedValue;

import com.socks.library.KLog;
import com.wpf.bookreader.DataInfo.ViewInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王朋飞 on 11-25-0025.
 * 完全显示整页
 */

public class GetPageList {
    private Context context;
    private TextPaint paint = new TextPaint();
    private ViewInfo viewInfo;
    private String oldFontName = "";
    private List<String> pageTextList = new ArrayList<>();
    private OnTextFinish onTextFinish;
    private LogTime logTime = new LogTime();

    public void getAPageText(Context context,String text,ViewInfo viewInfo,OnTextFinish onTextFinish) {
        this.context = context;
        this.viewInfo = viewInfo;
        this.onTextFinish = onTextFinish;
        init();
        KLog.a("开始分页");
        logTime.startTime = System.currentTimeMillis();
        new MyGetAPageAsyncTask().execute(text);
    }

    private void init() {
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        paint.setTextSize(viewInfo.getTextSize());
        paint.setColor(viewInfo.getTextColor());
        if(!oldFontName.equals(viewInfo.getTypefaceName()))
            paint.setTypeface("系统默认".equals(viewInfo.getTypefaceName())? Typeface.DEFAULT:
                    Typeface.createFromAsset(context.getAssets(), viewInfo.getTypefaceName()));
        oldFontName = viewInfo.getTypefaceName();
    }

    private class MyGetAPageAsyncTask extends AsyncTask<String,Integer,List<String>> {

        @Override
        protected List<String> doInBackground(String... strings) {
            return getPageTextList(strings[0]);
        }

        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);
            logTime.endTime = System.currentTimeMillis();
            if (!result.isEmpty()) {
                onTextFinish.onSuccess(result);
                KLog.a("分页结束" + "用时" + logTime.getUseTime());
            } else KLog.a("分页错误"+ "用时" + logTime.getUseTime());
        }
    }

    private List<String> getPageTextList(String text) {
        if(text == null || text.isEmpty()) return pageTextList;
        int len,start = 0,i = 0;
        while ((len = getPageLen()) < text.length()) {
            String str = calculate(text,start+len);
            if(str.trim().isEmpty() || str.equals("\n") || str.equals("\n\n")) {
                if(i >= 10) break;
                i++;
                continue;
            }
            pageTextList.add(str);
        }
        return pageTextList;
    }

    private int getPageLen() {
        int len = 0;
        for(String str : pageTextList)
            len+=str.length();
        return len;
    }

    private String calculate(String text,int startPosition) {
        int lens = 0;
        String[] strings = text.substring(startPosition).split("\n");
        int i = 0;
        for(String string : strings) {
            if(string.isEmpty()) i++;
            int start = 0,len = 1;
            while (start + len <= string.length()) {
                len = getLineLen(string, start);
                lens+=len;
                start+=len;
                if(viewInfo.getPaddingTop() + (i++) * (getTextHeight()+viewInfo.getSpacingVertical()) > viewInfo.getHeight()) {
                    return text.substring(startPosition, startPosition + lens - len);
                }
                len = 1;
            }
            lens++;
        }
        lens--;
        return text.substring(startPosition, startPosition+lens);
    }

    //文字宽度
    private float getTextWidth(String text) {
        return paint.measureText(text);
    }

    //文字高度
    private float getTextHeight() {
        Paint.FontMetrics fontMetrics= paint.getFontMetrics();
        return -fontMetrics.ascent-fontMetrics.descent;
    }

    private float getTextSize(int size) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, size, context.getResources().getDisplayMetrics());
    }

    private int getLineLen(String string,int start) {
//        return paint.breakText(string.substring(start),true,viewInfo.getWidth(),null);
        //比上面的快多了
        int len = 1;
        while (getTextWidth(string.substring(start, start+len)) <= viewInfo.getWidth())
            if (start+len++>=string.length()) break;
        return len-1;
    }

    public interface OnTextFinish {
        void onSuccess(List<String> chapterTextList);
    }
}
