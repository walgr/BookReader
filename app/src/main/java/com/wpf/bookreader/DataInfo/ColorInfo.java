package com.wpf.bookreader.DataInfo;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 王朋飞 on 12-5-0005.
 * 配色信息
 */

public class ColorInfo implements Parcelable {
    private int colorBack = Color.BLACK;
    private int colorText = Color.GRAY;

    public ColorInfo() {

    }

    public ColorInfo(int colorBack, int colorText) {
        this.colorBack = colorBack;
        this.colorText = colorText;
    }

    public int getColorBack() {
        return colorBack;
    }

    public ColorInfo setColorBack(int colorBack) {
        this.colorBack = colorBack;
        return this;
    }

    public int getColorText() {
        return colorText;
    }

    public ColorInfo setColorText(int colorText) {
        this.colorText = colorText;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.colorBack);
        dest.writeInt(this.colorText);
    }

    protected ColorInfo(Parcel in) {
        this.colorBack = in.readInt();
        this.colorText = in.readInt();
    }

    public static final Creator<ColorInfo> CREATOR = new Creator<ColorInfo>() {
        @Override
        public ColorInfo createFromParcel(Parcel source) {
            return new ColorInfo(source);
        }

        @Override
        public ColorInfo[] newArray(int size) {
            return new ColorInfo[size];
        }
    };
}
