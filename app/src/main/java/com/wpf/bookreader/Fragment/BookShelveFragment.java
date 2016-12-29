package com.wpf.bookreader.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socks.library.KLog;
import com.wpf.bookreader.Adapter.BookListAdapter;
import com.wpf.bookreader.Adapter.OnItemClickListener;
import com.wpf.bookreader.Adapter.OnItemLongClickListener;
import com.wpf.bookreader.BookReaderApplication;
import com.wpf.bookreader.DataBase.BookInfo;
import com.wpf.bookreader.DataBase.BookManager;
import com.wpf.bookreader.DataBase.ChapterInfo;
import com.wpf.bookreader.DataBase.ChapterManager;
import com.wpf.bookreader.R;
import com.wpf.bookreader.ReadActivity;

import java.util.ArrayList;
import java.util.List;

public class BookShelveFragment extends BaseFragment implements
        OnItemClickListener,
        OnItemLongClickListener {

    private RecyclerView bookList;
    private BookListAdapter bookListAdapter;
    private List<BookInfo> bookInfoList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater,container,R.layout.fragment_book_shelve);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void initView() {
        bookList = (RecyclerView) view.findViewById(R.id.bookList);
        bookList.setLayoutManager(new GridLayoutManager(getContext(),3, LinearLayoutManager.VERTICAL,false));
        bookListAdapter = new BookListAdapter(bookInfoList);
        bookListAdapter.setOnItemClickListener(this);
        bookListAdapter.setOnItemLongClickListener(this);
        bookList.setAdapter(bookListAdapter);
        doNotify();
    }

    public void doNotify() {
        if(bookListAdapter != null) {
            bookInfoList = BookManager.getBookList();
            bookListAdapter.setBookInfoList(bookInfoList);
        }
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getActivity(), ReadActivity.class);
        BookReaderApplication.bookInfo = bookInfoList.get(position);
        intent.putParcelableArrayListExtra("ChapterInfoList", getChapterList(position));
        startActivity(intent);
    }

    private ArrayList<ChapterInfo> getChapterList(int position) {
        return (ArrayList<ChapterInfo>) ChapterManager.getChapterInfoList(bookInfoList.get(position).bookUrl);
    }

    @Override
    public boolean onLongClick(int position) {
        String name = bookInfoList.get(position).getBookName();
        KLog.e("删除书籍" + name);
        BookManager.delBook(name);
        bookInfoList.remove(bookInfoList.get(position));
        bookListAdapter.notifyDataSetChanged();
        return true;
    }
}
