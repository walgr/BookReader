package com.wpf.bookreader.Utils;

import android.os.AsyncTask;

import com.socks.library.KLog;
import com.wpf.bookreader.DataBase.BookInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

/**
 * Created by 王朋飞 on 12-19-0019.
 * 获取书籍信息
 */

public class BookInfoManager {

    private OnBookScanFinish onBookScanFinish;

    public void get(String url,OnBookScanFinish onBookScanFinish) {
        this.onBookScanFinish = onBookScanFinish;
        new BookInfoScanAsyncTask().execute(url);
        KLog.a("开始扫描网页信息");
    }

    private class BookInfoScanAsyncTask extends AsyncTask<String,Void,BookInfo> {

        @Override
        protected BookInfo doInBackground(String... strings) {
            BookInfo bookInfo = new BookInfo();
            try {
                Document document = Jsoup.parse(new URL(strings[0]),5000);
                String tabStr = document.select("div.lb_fm").text();
                Element img = document.select("img[src$=.jpg]").first();
                bookInfo.bookUrl = getListUrl(strings[0]);
                bookInfo.bookName = tabStr.split(" ")[0];
                bookInfo.bookImgUrl = img.attr("src");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bookInfo;
        }

        @Override
        protected void onPostExecute(BookInfo bookInfo) {
            super.onPostExecute(bookInfo);
            if(bookInfo == null || bookInfo.bookName.isEmpty()) {
                KLog.a("扫描书籍信息失败");
                return;
            }
            KLog.a("扫描书籍信息成功");
            onBookScanFinish.onSuccess(bookInfo);
        }
    }

    private String getListUrl(String url) {
        String listUrl = url.replace("book","booklist");
        if(listUrl.endsWith("/")) listUrl = listUrl.substring(0,listUrl.length()-1);
        listUrl += ".html";
        return listUrl;
    }

    public interface OnBookScanFinish {
        void onSuccess(BookInfo bookInfo);
    }
}
