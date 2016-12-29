package com.wpf.bookreader.DataBase;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

/**
 * Created by 王朋飞 on 12-5-0005.
 * 章节信息
 */

@Entity
public class ChapterInfo implements Parcelable {

    private int position = 0;
    @Id
    //章节url地址
    private String url = "";

    //书籍地址
    private String bookUrl = "";

    //章节名称
    private String chapterName = "";

    //章节内容
    private String chapterPageContent = "";

    @Transient
    //章节分页内容
    private List<String> chapterPageList;

    @Generated(hash = 1686365921)
    public ChapterInfo(int position, String url, String bookUrl, String chapterName,
            String chapterPageContent) {
        this.position = position;
        this.url = url;
        this.bookUrl = bookUrl;
        this.chapterName = chapterName;
        this.chapterPageContent = chapterPageContent;
    }

    @Generated(hash = 1687309083)
    public ChapterInfo() {
    }

    public int getPosition() {
        return position;
    }

    public ChapterInfo setPosition(int position) {
        this.position = position;
        return this;
    }

    public String getChapterName() {
        return chapterName;
    }

    public ChapterInfo setChapterName(String chapterName) {
        this.chapterName = chapterName;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ChapterInfo setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getBookUrl() {
        return this.bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getChapterPageContent() {
        return chapterPageContent;
    }

    public ChapterInfo setChapterPageContent(String chapterPageContent) {
        this.chapterPageContent = chapterPageContent;
        return this;
    }

    public List<String> getChapterPageList() {
        return chapterPageList;
    }

    public ChapterInfo setChapterPageList(List<String> chapterPageList) {
        this.chapterPageList = chapterPageList;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.position);
        dest.writeString(this.url);
        dest.writeString(this.bookUrl);
        dest.writeString(this.chapterName);
        dest.writeString(this.chapterPageContent);
        dest.writeStringList(this.chapterPageList);
    }

    protected ChapterInfo(Parcel in) {
        this.position = in.readInt();
        this.url = in.readString();
        this.bookUrl = in.readString();
        this.chapterName = in.readString();
        this.chapterPageContent = in.readString();
        this.chapterPageList = in.createStringArrayList();
    }

    @Transient
    public static final Creator<ChapterInfo> CREATOR = new Creator<ChapterInfo>() {
        @Override
        public ChapterInfo createFromParcel(Parcel source) {
            return new ChapterInfo(source);
        }

        @Override
        public ChapterInfo[] newArray(int size) {
            return new ChapterInfo[size];
        }
    };
}
