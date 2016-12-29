package com.wpf.bookreader.DataBase;

import android.os.Parcel;
import android.os.Parcelable;

import com.wpf.bookreader.DataInfo.Position;

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

    //作者
    public String bookAuthor = "";

    //章节位置记录
    public int chapterPosition = 0;

    //页码位置记录
    public int pagePosition = 0;

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

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public void setPagePosition(int pagePosition) {
        this.pagePosition = pagePosition;
    }

    public void setChapterPosition(int chapterPosition) {
        this.chapterPosition = chapterPosition;
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getBookAuthor() {
        return this.bookAuthor;
    }

    public int getChapterPosition() {
        return this.chapterPosition;
    }

    public int getPagePosition() {
        return this.pagePosition;
    }

    public Position getPosition() {
        return new Position(this.chapterPosition,this.pagePosition);
    }

    public BookInfo() {
    }

    protected BookInfo(Parcel in) {
        this.bookName = in.readString();
        this.bookUrl = in.readString();
        this.bookImgUrl = in.readString();
        this.bookAuthor = in.readString();
        this.chapterPosition = in.readInt();
        this.pagePosition = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bookName);
        dest.writeString(this.bookUrl);
        dest.writeString(this.bookImgUrl);
        dest.writeString(this.bookAuthor);
        dest.writeInt(this.chapterPosition);
        dest.writeInt(this.pagePosition);
    }

    @Generated(hash = 926482824)
    public BookInfo(String bookName, String bookUrl, String bookImgUrl, String bookAuthor,
            int chapterPosition, int pagePosition) {
        this.bookName = bookName;
        this.bookUrl = bookUrl;
        this.bookImgUrl = bookImgUrl;
        this.bookAuthor = bookAuthor;
        this.chapterPosition = chapterPosition;
        this.pagePosition = pagePosition;
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
