package com.wpf.bookreader.DataInfo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 王朋飞 on 12-19-0019.
 * 书籍信息
 */

public class BookInfo implements Parcelable {

    //书名
    public String bookName = "";

    //书籍网络地址
    public String bookUrl = "";

    //书籍封面图片地址
    public String bookImgUrl = "";

    public BookInfo setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public BookInfo setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
        return this;
    }

    public BookInfo setBookImgUrl(String bookImgUrl) {
        this.bookImgUrl = bookImgUrl;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bookName);
        dest.writeString(this.bookUrl);
        dest.writeString(this.bookImgUrl);
    }

    public BookInfo() {
    }

    protected BookInfo(Parcel in) {
        this.bookName = in.readString();
        this.bookUrl = in.readString();
        this.bookImgUrl = in.readString();
    }

    public static final Parcelable.Creator<BookInfo> CREATOR = new Parcelable.Creator<BookInfo>() {
        @Override
        public BookInfo createFromParcel(Parcel source) {
            return new BookInfo(source);
        }

        @Override
        public BookInfo[] newArray(int size) {
            return new BookInfo[size];
        }
    };
}
