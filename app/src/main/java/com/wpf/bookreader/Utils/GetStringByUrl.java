package com.wpf.bookreader.Utils;

import android.os.AsyncTask;
import android.text.Html;

import com.socks.library.KLog;
import com.wpf.bookreader.BookReaderApplication;
import com.wpf.bookreader.DataBase.ChapterInfo;
import com.wpf.bookreader.DataBase.ChapterManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 王朋飞 on 11-21-0021.
 * 获取网页信息
 */

public class GetStringByUrl {

    private OnListFinish onListFinish;
    private OnTextFinish onTextFinish;

    public void getChapterListByUrl(String url, OnListFinish onListFinish) {
        this.onListFinish = onListFinish;
        new MyGetListAsyncTask().execute(url);
        KLog.a("正在获取小说目录");
    }

    public void getChapterContentByUrl(String url, String name, OnTextFinish onTextFinish) {
        this.onTextFinish = onTextFinish;
        new MyGetTextAsyncTask().execute(url,name);
        KLog.a("正在获取章节"+name+"内容");
    }

    private class MyGetListAsyncTask extends AsyncTask<String,Integer,List<ChapterInfo>> {
        List<ChapterInfo> chapterList = new ArrayList<>();
        @Override
        protected List<ChapterInfo> doInBackground(String... strings) {
            try {
                String str = getInfoByUrl(strings[0]);
                List<String> titles = getSubString(str,"<div class=\"bgg\">","</div>");
                Collections.reverse(titles);
                int i = 0;
                for(String string : titles) {
                    chapterList.add(getChapter(string).setPosition(i++));
                }
                ChapterManager.saveChapterInfoList(chapterList);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return chapterList;
        }

        @Override
        protected void onPostExecute(List<ChapterInfo> result) {
            super.onPostExecute(result);
            if(!result.isEmpty()) {
                KLog.a("获取小说目录完成");
                onListFinish.onSuccess(result);
            } else {
                KLog.a("获取小说目录失败");
            }
        }
    }

    private class MyGetTextAsyncTask extends AsyncTask<String,Integer,String> {
        private String name = "";
        @Override
        protected String doInBackground(String... strings) {
            name = strings[1];
            return getPageListInfo(strings[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(!result.isEmpty()) {
                KLog.a("获取章节"+name+"内容完成");
                onTextFinish.onSuccess(result);
            } else KLog.a("获取章节"+name+"内容失败");
        }
    }

    //url 网址 name 章节名称
    private String getPageListInfo(String url) {
        ChapterInfo chapterInfo = ChapterManager.getChapterInfo(url);
        if(chapterInfo != null && !chapterInfo.getChapterPageContent().isEmpty())
            return chapterInfo.getChapterPageContent();
        String string = "";
        try {
            string = getTitleString(getInfoByUrl(url),"<div id=\"nr1\">","</div>").get(0);
            List<String> noShowList = getTitleString(string,"<script>","</script>");
            noShowList.addAll(getSubString(string,"<script>","</script>"));
            for(String s : noShowList) {
                string = string.replace("<script>"+s+"</script>","");
            }
            string = String.valueOf(Html.fromHtml(string));
        } catch (IndexOutOfBoundsException ignored) {} catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    private String getInfoByUrl(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = BookReaderApplication.okHttpClient.newCall(request).execute();
        return new String(response.body().bytes(),"GBK");
    }

    private List<String> getSubString(String str,String start,String end) {
        List<String> strings = new ArrayList<>();
        if(!(str.contains(start) && str.contains(end))) return strings;
        List<Integer> indexList = new ArrayList<>();
        int index_start = 0;
        while ((index_start = str.indexOf(start, index_start+1)) <= str.length()) {
            if(index_start == -1) {index_start++;continue;}
            if(indexList.isEmpty()) {
                indexList.add(0, index_start);
                int index_end = str.indexOf(end,index_start);
                String string = str.substring(index_start+start.length(),index_end);
                strings.add(string);
                continue;
            }
            if (index_start > indexList.get(0)) {
                indexList.add(0, index_start);
                int index_end = str.indexOf(end,index_start);
                String string = str.substring(index_start+start.length(),index_end);
                strings.add(string);
            } else break;
        }
        return strings;
    }

    private List<String> getTitleString(String str, String start, String end) {
        List<String> strings = new ArrayList<>();
        List<Integer> indexList = new ArrayList<>();
        int index_start;
        while ((index_start = str.indexOf(start)) <= str.length()) {
            if(indexList.isEmpty()) {
                indexList.add(0, index_start);
                int index_end = str.indexOf(end,index_start);
                String string = str.substring(index_start+start.length(),index_end);
                strings.add(string);
                continue;
            }
            if (index_start > indexList.get(0)) {
                indexList.add(0, index_start);
                int index_end = str.indexOf(end,index_start);
                String string = str.substring(index_start+start.length(),index_end);
                strings.add(string);
            } else break;
        }
        return strings;
    }

    private ChapterInfo getChapter(String string) {
        ChapterInfo chapterInfo = new ChapterInfo();
        try {
            chapterInfo.setUrl(getTitleString(string,"<a href='","'>").get(0));
            chapterInfo.setChapterName(getTitleString(string,">","</a>").get(0));
        } catch (IndexOutOfBoundsException ignored) {}

        return chapterInfo;
    }

    public interface OnListFinish {
        void onSuccess(List<ChapterInfo> result);
    }

    public interface OnTextFinish {
        void onSuccess(String textInfo);
    }
}
