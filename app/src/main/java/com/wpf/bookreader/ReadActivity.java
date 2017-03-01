package com.wpf.bookreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wpf.bookreader.DataBase.BookInfo;
import com.wpf.bookreader.DataBase.ChapterInfo;
import com.wpf.bookreader.Widget.ReadView;

import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity {

    private ReadView readView;
    private BookInfo bookInfo = BookReaderApplication.bookInfo;
    private ArrayList<ChapterInfo> bookChapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        init();
    }

    private void init() {
        readView = (ReadView) findViewById(R.id.readView);
        bookChapterList = getIntent().getParcelableArrayListExtra("ChapterInfoList");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            readView.initView();
            readView.setBookInfo(bookInfo);
            readView.setChapterList(bookChapterList);
            if(!readView.isBackResult()) {
                readView.setCurChapterPosition(bookInfo.getChapterPosition());
                readView.setCurPagePosition(bookInfo.getPagePosition());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        readView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        readView.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        readView.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onBackPressed() {
        if(readView.actionSet.isShow || readView.actionDetailSet.isShow) {
            readView.notShowAction();
        } else finish();
    }

    @Override
    public void finish() {
        readView.onDestroy();
        super.finish();
    }
}
