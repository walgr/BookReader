package com.wpf.bookreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wpf.bookreader.DataInfo.BookInfo;
import com.wpf.bookreader.Widget.BookInfoView;

public class BookInfoActivity extends AppCompatActivity {

    private BookInfoView bookInfoView;
    private BookInfo bookInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        if(intent == null) return;
        bookInfo = intent.getParcelableExtra("BookInfo");
        bookInfoView = (BookInfoView) findViewById(R.id.bookInfoView);
        bookInfoView.setBookInfo(bookInfo);
    }
}
