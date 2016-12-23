package com.wpf.bookreader.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wpf.bookreader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王朋飞 on 11-22-0022.
 * 小说目录适配器
 */

public class FontListAdapter extends RecyclerView.Adapter<FontListAdapter.MyViewHolder> {

    private List<String> fontList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public FontListAdapter(List<String> fontList) {
        this.fontList.add(0,"系统默认");
        this.fontList.addAll(fontList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_font,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String fontName = fontList.get(position);
        if(fontName.contains("."))
            fontName = fontName.split("\\.")[0];
        holder.textView_Item.setText(fontName);
    }

    @Override
    public int getItemCount() {
        return fontList.size();
    }

    public void setFontList(List<String> fontList) {
        if(fontList == null) return;
        this.fontList.add(0,"系统默认");
        this.fontList.addAll(fontList);
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
