package com.wpf.bookreader.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wpf.bookreader.DataBase.ChapterInfo;
import com.wpf.bookreader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王朋飞 on 11-22-0022.
 * 小说目录适配器
 */

public class BookChapterListAdapter extends RecyclerView.Adapter<BookChapterListAdapter.MyViewHolder> {

    private List<ChapterInfo> bookList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public BookChapterListAdapter(List<ChapterInfo> bookList) {
        this.bookList = bookList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_charpter,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView_Item.setText(bookList.get(position).getChapterName());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void setBookList(List<ChapterInfo> bookList) {
        if(bookList == null) return;
        this.bookList = bookList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private TextView textView_Item;

        MyViewHolder(View itemView) {
            super(itemView);
            textView_Item = (TextView) itemView.findViewById(R.id.textView_Title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onItemClickListener == null) return;
            onItemClickListener.onClick(getAdapterPosition());
        }
    }
}
