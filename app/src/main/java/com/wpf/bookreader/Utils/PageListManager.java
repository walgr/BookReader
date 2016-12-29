package com.wpf.bookreader.Utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.TextPaint;

import com.socks.library.KLog;
import com.wpf.bookreader.DataInfo.ViewInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王朋飞 on 11-25-0025.
 * 完全显示整页
 */

public class PageListManager {
    private Context context;
    private TextPaint paint = new TextPaint();
    private ViewInfo viewInfo;
    private String oldFontName = "";
    private OnTextFinish onTextFinish;
    private LogTime logTime = new LogTime();

    public void getPageText(Context context, String text, ViewInfo viewInfo, OnTextFinish onTextFinish) {
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
                KLog.a("分页结束" + "用时" + logTime.getUseTime());
                onTextFinish.onSuccess(result);
            } else KLog.a("分页错误"+ "用时" + logTime.getUseTime());
        }
    }

    private List<String> getPageTextList(String text) {
        List<String> pageTextList = new ArrayList<>();
        if(text == null || text.isEmpty()) return pageTextList;
        int len,start = 0,i = 0;
        while ((len = getPageLen(pageTextList)) < text.length()) {
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

    private int getPageLen(List<String> pageTextList) {
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

    //文字高度
    private float getTextHeight() {
        Paint.FontMetrics fontMetrics= paint.getFontMetrics();
        return -fontMetrics.ascent-fontMetrics.descent;
    }

    private int getLineLen(String string,int start) {
        return paint.breakText(string.substring(start),true,viewInfo.getWidth(),null);
    }

    public interface OnTextFinish {
        void onSuccess(List<String> chapterTextList);
    }
}
