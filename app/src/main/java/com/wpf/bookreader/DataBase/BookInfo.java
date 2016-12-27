package com.wpf.bookreader.DataBase;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by 王朋飞 on 12-19-0019.
 * 书籍信息
 */

@Entity
public class BookInfo implements Parcelable {

    @Id
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

    public String getBookName() {
        return this.bookName;
    }

    public String getBookUrl() {
        return this.bookUrl;
    }

    public String getBookImgUrl() {
        return this.bookImgUrl;
    }

    public BookInfo() {
    }

    protected BookInfo(Parcel in) {
        this.bookName = in.readString();
        this.bookUrl = in.readString();
        this.bookImgUrl = in.readString();
    }

    @Generated(hash = 1872803641)
    public BookInfo(String bookName, String bookUrl, String bookImgUrl) {
        this.bookName = bookName;
        this.bookUrl = bookUrl;
        this.bookImgUrl = bookImgUrl;
    }

    @Transient
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
