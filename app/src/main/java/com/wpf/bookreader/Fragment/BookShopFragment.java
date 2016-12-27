package com.wpf.bookreader.Fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.wpf.bookreader.DataBase.BookInfo;
import com.wpf.bookreader.DataBase.BookManager;
import com.wpf.bookreader.DataBase.ChapterInfo;
import com.wpf.bookreader.R;
import com.wpf.bookreader.Utils.GetBookInfoByUrl;
import com.wpf.bookreader.Utils.GetStringByUrl;

import java.util.List;

public class BookShopFragment extends BaseFragment implements
        View.OnClickListener {

    private WebView bookShop;
    private FloatingActionButton saveInfoBookShelve;

    private String mainUrl = "http://m.qu.la/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater,container,R.layout.fragment_book_shop);
    }

    public void initView() {
        bookShop = (WebView) view.findViewById(R.id.web_BookShop);
        saveInfoBookShelve = (FloatingActionButton) view.findViewById(R.id.saveInfoBookShelve);

        bookShop.loadUrl(mainUrl);
        //禁止广告
//        WebSettings webSettings = bookShop.getSettings();
//        webSettings.setJavaScriptEnabled(true);
        bookShop.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
                return false;

            }
        });
        saveInfoBookShelve.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        new GetStringByUrl().getChapterListByUrl(getListUrl(bookShop.getUrl()), new GetStringByUrl.OnListFinish() {
            @Override
            public void onSuccess(List<ChapterInfo> result) {
                if(!result.isEmpty()) {
                    new GetBookInfoByUrl().get(bookShop.getUrl(), new GetBookInfoByUrl.OnBookScanFinish() {
                        @Override
                        public void onSuccess(BookInfo bookInfo) {
                            Toast.makeText(getContext(),"添加书籍" + bookInfo.getBookName(),Toast.LENGTH_SHORT).show();
                            BookManager.addBook(bookInfo);
                            notice();
                        }
                    });
                }
            }
        });
    }

    private void notice() {
        getFragmentManager().getFragments().get(0).onResume();
    }

    private String getListUrl(String url) {
        String listUrl = url.replace("book","booklist");
        listUrl = listUrl.substring(0,listUrl.length()-2) + ".html";
        return listUrl;
    }
}
