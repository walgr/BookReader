package com.wpf.bookreader.Widget;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wpf.bookreader.Adapter.BookChapterListAdapter;
import com.wpf.bookreader.Adapter.OnItemClickListener;
import com.wpf.bookreader.DataBase.ChapterInfo;
import com.wpf.bookreader.R;
import com.wpf.bookreader.DataBase.BookInfo;
import com.wpf.bookreader.ReadActivity;
import com.wpf.bookreader.Utils.GetStringByUrl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王朋飞 on 12-19-0019.
 * 书籍展示界面
 */

public class BookInfoView extends LinearLayout implements
        View.OnClickListener,
        OnItemClickListener {

    private BookInfo bookInfo = new BookInfo();

    private ArrayList<ChapterInfo> bookChapterList = new ArrayList<>();
    private RecyclerView bookChapterListView;
    private BookChapterListAdapter bookChapterListAdapter;
    private FloatingActionButton read;

    public BookInfoView(Context context) {
        this(context,null);
    }

    public BookInfoView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BookInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.view_book_info,this,false);
        bookChapterListView = (RecyclerView) view.findViewById(R.id.bookChapterList);
        read = (FloatingActionButton) view.findViewById(R.id.read);

        bookChapterListView.setLayoutManager(new LinearLayoutManager(getContext()));
        bookChapterListAdapter = new BookChapterListAdapter(bookChapterList);
        bookChapterListAdapter.setOnItemClickListener(this);
        bookChapterListView.setAdapter(bookChapterListAdapter);
        addView(view);
    }

    private void initData() {
        read.setOnClickListener(this);

        new GetStringByUrl().getChapterListByUrl(bookInfo.bookUrl, new GetStringByUrl.OnListFinish() {
            @Override
            public void onSuccess(List<ChapterInfo> result) {
                bookChapterList = (ArrayList<ChapterInfo>) result;
                bookChapterListAdapter.setBookList(result);
            }
        });
    }

    public BookInfoView setBookInfo(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
        initData();
        return this;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getContext(), ReadActivity.class);
        intent.putParcelableArrayListExtra("ChapterInfoList",bookChapterList);
        intent.putExtra("ClickChapterInfo",bookChapterList.get(position));
        getContext().startActivity(intent);
    }
}
