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

import com.wpf.bookreader.Adapter.BookListAdapter;
import com.wpf.bookreader.Adapter.OnItemClickListener;
import com.wpf.bookreader.BookInfoActivity;
import com.wpf.bookreader.DataBase.BookInfo;
import com.wpf.bookreader.DataBase.BookManager;
import com.wpf.bookreader.R;

import java.util.ArrayList;
import java.util.List;

public class BookShelveFragment extends BaseFragment implements
        OnItemClickListener {

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
        bookList.setAdapter(bookListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        bookInfoList = BookManager.getBookList();
        bookListAdapter.setBookInfoList(bookInfoList);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getActivity(), BookInfoActivity.class);
        intent.putExtra("BookInfo",bookInfoList.get(position));
        startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        //getActivity().overridePendingTransition(R.anim.actionset_up,R.anim.zoom_enter);
    }
}
