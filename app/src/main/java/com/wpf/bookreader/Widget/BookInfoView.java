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
import com.wpf.bookreader.BookInfoActivity;
import com.wpf.bookreader.DataBase.BookInfo;
import com.wpf.bookreader.DataBase.ChapterInfo;
import com.wpf.bookreader.DataBase.ChapterManager;
import com.wpf.bookreader.R;
import com.wpf.bookreader.Utils.GetStringByUrl;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

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
    private FloatingActionButton btn;
    private boolean isDown = true;

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
        btn = (FloatingActionButton) view.findViewById(R.id.btn);

        bookChapterListView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,isDown=!isDown));
        bookChapterListAdapter = new BookChapterListAdapter(bookChapterList);
        bookChapterListAdapter.setOnItemClickListener(this);
        bookChapterListView.setAdapter(bookChapterListAdapter);

        addView(view);
    }

    private void initData() {
        btn.setOnClickListener(this);
        bookChapterList = (ArrayList<ChapterInfo>) ChapterManager.getChapterInfoList(bookInfo.bookUrl);
        bookChapterListAdapter.setBookList(bookChapterList);
        new GetStringByUrl().getChapterListByUrl(bookInfo.bookUrl, new GetStringByUrl.OnListFinish() {
            @Override
            public void onSuccess(List<ChapterInfo> result) {
                bookChapterList = (ArrayList<ChapterInfo>) result;
                bookChapterListAdapter.setBookList(bookChapterList);
            }
        });
    }

    public BookInfoView setBookInfo(BookInfo bookInfo) {
        if(bookInfo == null) return this;
        this.bookInfo = bookInfo;
        initData();
        return this;
    }

    @Override
    public void onClick(View view) {
        bookChapterListView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,isDown=!isDown));
        bookChapterListAdapter.notifyDataSetChanged();
        if(isDown) bookChapterListView.scrollToPosition(bookChapterList.size()-1);
    }

    @Override
    public void onClick(int position) {
        BookInfoActivity bookInfoActivity = ((BookInfoActivity)getContext());
        Intent intent = bookInfoActivity.getIntent();
        intent.putExtra("Position",position);
        bookInfoActivity.setResult(RESULT_OK,intent);
        bookInfoActivity.finish();
    }
}
