package com.wpf.bookreader.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wpf.bookreader.DataInfo.ColorInfo;
import com.wpf.bookreader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王朋飞 on 12-5-0005.
 * 配色适配器
 */

public class ColorListAdapter extends RecyclerView.Adapter<ColorListAdapter.MyViewHolder> {

    private OnItemClickListener onItemClickListener;
    private int selectPos;
    private List<ColorInfo> colorInfoList = new ArrayList<>();
    private Context context;

    public ColorListAdapter(List<ColorInfo> colorInfoList) {
        this.colorInfoList = colorInfoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.view_color,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Drawable drawable = ContextCompat.getDrawable(context,R.drawable.ciracleback);
        holder.backView.setBackground(drawable);
        holder.backView.setBackgroundColor(colorInfoList.get(position).getColorBack());
        holder.textViewColor.setTextColor(colorInfoList.get(position).getColorText());
        holder.imageViewSelect.setVisibility(position == selectPos ?View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return colorInfoList.size();
    }

    public ColorListAdapter setSelectPos(int selectPos) {
        this.selectPos = selectPos;
        notifyDataSetChanged();
        return this;
    }

    public ColorListAdapter setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        private View backView;
        private TextView textViewColor;
        private ImageView imageViewSelect;

        MyViewHolder(View itemView) {
            super(itemView);
            backView = itemView.findViewById(R.id.backView);
            textViewColor = (TextView) itemView.findViewById(R.id.textViewColor);
            imageViewSelect = (ImageView) itemView.findViewById(R.id.image_select);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onClick(getAdapterPosition());
        }
    }


}
