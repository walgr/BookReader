package com.wpf.bookreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wpf.bookreader.DataBase.BookInfo;
import com.wpf.bookreader.Widget.BookInfoView;

public class BookInfoActivity extends AppCompatActivity {

    private BookInfoView bookInfoView;
    private BookInfo bookInfo = BookReaderApplication.bookInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        init();
    }

    private void init() {
        bookInfoView = (BookInfoView) findViewById(R.id.bookInfoView);
        bookInfoView.setBookInfo(bookInfo);
    }
}
