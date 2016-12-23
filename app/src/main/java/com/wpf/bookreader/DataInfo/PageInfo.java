package com.wpf.bookreader.DataInfo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 王朋飞 on 12-21-0021.
 * 分页信息
 */

public class PageInfo implements Parcelable {
    private int position;
    private String text;

    public PageInfo(int position, String text) {
        this.position = position;
        this.text = text;
    }

    public int getPosition() {
        return position;
    }

    public PageInfo setPosition(int position) {
        this.position = position;
        return this;
    }

    public String getText() {
        return text;
    }

    public PageInfo setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.position);
        dest.writeString(this.text);
    }

    public PageInfo() {
    }

    protected PageInfo(Parcel in) {
        this.position = in.readInt();
        this.text = in.readString();
    }

    public static final Parcelable.Creator<PageInfo> CREATOR = new Parcelable.Creator<PageInfo>() {
        @Override
        public PageInfo createFromParcel(Parcel source) {
            return new PageInfo(source);
        }

        @Override
        public PageInfo[] newArray(int size) {
            return new PageInfo[size];
        }
    };
}
