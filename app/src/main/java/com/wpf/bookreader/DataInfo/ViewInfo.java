package com.wpf.bookreader.DataInfo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 王朋飞 on 11-25-0025.
 *
 */

public class ViewInfo implements Parcelable {

    private int width;
    private int height;
    private int textSize;
    private int textColor;
    private int spacingVertical;
    private int paddingTop;
    private int paddingBottom;
    private int paddingLeft;
    private int battery;
    private String typefaceName = "系统默认";
    private String bookName = "太谷神王";
    private String time = "";

    public ViewInfo() {

    }

    public ViewInfo(int width, int battery, String bookName, int textColor, int height,
                    int paddingBottom, int paddingLeft, int paddingTop, int textSize, int spacingVertical,
                    String time, String typefaceName) {
        this.width = width;
        this.height = height;
        this.battery = battery;
        this.bookName = bookName;
        this.textColor = textColor;
        this.paddingBottom = paddingBottom;
        this.paddingLeft = paddingLeft;
        this.paddingTop = paddingTop;
        this.textSize = textSize;
        this.spacingVertical = spacingVertical;
        this.time = time;
        this.typefaceName = typefaceName;
    }

    public ViewInfo(ViewInfo viewInfo) {
        this.width = viewInfo.getWidth() + 2 * viewInfo.getPaddingLeft();
        this.height = viewInfo.getHeight()+viewInfo.getPaddingTop()+viewInfo.getPaddingBottom();
        this.battery = viewInfo.getBattery();
        this.bookName = viewInfo.getBookName();
        this.textColor = viewInfo.getTextColor();
        this.paddingBottom = viewInfo.getPaddingBottom();
        this.paddingLeft = viewInfo.getPaddingLeft();
        this.paddingTop = viewInfo.getPaddingTop();
        this.textSize = viewInfo.getTextSize();
        this.spacingVertical = viewInfo.getSpacingVertical();
        this.time = viewInfo.getTime();
        this.typefaceName = viewInfo.getTypefaceName();
    }

    public int getTextColor() {
        return textColor;
    }

    public ViewInfo setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public int getHeight() {
        return height - paddingTop - paddingBottom;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public int getTextSize() {
        return textSize;
    }

    public ViewInfo setTextSize(int sizeText) {
        this.textSize = sizeText;
        return this;
    }

    public int getSpacingVertical() {
        return spacingVertical;
    }

    public void setSpacingVertical(int spacingVertical) {
        this.spacingVertical = spacingVertical;
    }

    public String getTypefaceName() {
        return typefaceName;
    }

    public ViewInfo setTypefaceName(String typefaceName) {
        this.typefaceName = typefaceName;
        return this;
    }

    public int getWidth() {
        return width - 2*paddingLeft;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getBattery() {
        return battery;
    }

    public ViewInfo setBattery(int battery) {
        this.battery = battery;
        return this;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getTime() {
        return time;
    }

    public ViewInfo setTime(String time) {
        this.time = time;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeInt(this.textSize);
        dest.writeInt(this.textColor);
        dest.writeInt(this.spacingVertical);
        dest.writeInt(this.paddingTop);
        dest.writeInt(this.paddingBottom);
        dest.writeInt(this.paddingLeft);
        dest.writeInt(this.battery);
        dest.writeString(this.typefaceName);
        dest.writeString(this.bookName);
        dest.writeString(this.time);
    }

    protected ViewInfo(Parcel in) {
        this.width = in.readInt();
        this.height = in.readInt();
        this.textSize = in.readInt();
        this.textColor = in.readInt();
        this.spacingVertical = in.readInt();
        this.paddingTop = in.readInt();
        this.paddingBottom = in.readInt();
        this.paddingLeft = in.readInt();
        this.battery = in.readInt();
        this.typefaceName = in.readString();
        this.bookName = in.readString();
        this.time = in.readString();
    }

    public static final Creator<ViewInfo> CREATOR = new Creator<ViewInfo>() {
        @Override
        public ViewInfo createFromParcel(Parcel source) {
            return new ViewInfo(source);
        }

        @Override
        public ViewInfo[] newArray(int size) {
            return new ViewInfo[size];
        }
    };
}
