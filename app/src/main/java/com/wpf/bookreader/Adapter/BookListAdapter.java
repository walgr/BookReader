package com.wpf.bookreader.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wpf.bookreader.DataInfo.BookInfo;
import com.wpf.bookreader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王朋飞 on 12-19-0019.
 *
 */

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.MyViewHolder> {

    private OnItemClickListener onItemClickListener;
    private Context context;
    private List<BookInfo> bookInfoList = new ArrayList<>();

    public BookListAdapter() {
    }

    public BookListAdapter(List<BookInfo> bookInfoList) {
        this.bookInfoList = bookInfoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.view_book,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BookInfo bookInfo = bookInfoList.get(position);
        if(!bookInfo.bookImgUrl.isEmpty())
            Glide.with(context).load(bookInfo.bookImgUrl).into(holder.bookImg);
        holder.bookName.setText(bookInfo.bookName);
    }

    @Override
    public int getItemCount() {
        return bookInfoList.size();
    }

    public BookListAdapter setBookInfoList(List<BookInfo> bookInfoList) {
        this.bookInfoList = bookInfoList;
        return this;
    }

    public BookListAdapter setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private ImageView bookImg;
        private TextView bookName;

        MyViewHolder(View itemView) {
            super(itemView);
            bookImg = (ImageView) itemView.findViewById(R.id.bookImg);
            bookName = (TextView) itemView.findViewById(R.id.bookName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onClick(getAdapterPosition());
        }
    }
}
