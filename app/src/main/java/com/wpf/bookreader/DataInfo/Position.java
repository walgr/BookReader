package com.wpf.bookreader.DataInfo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 王朋飞 on 12-21-0021.
 * 位置
 */

public class Position implements Parcelable {
    public int chapterPosition;
    public int pagePosition;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.chapterPosition);
        dest.writeInt(this.pagePosition);
    }

    public Position() {
    }

    protected Position(Parcel in) {
        this.chapterPosition = in.readInt();
        this.pagePosition = in.readInt();
    }

    public static final Parcelable.Creator<Position> CREATOR = new Parcelable.Creator<Position>() {
        @Override
        public Position createFromParcel(Parcel source) {
            return new Position(source);
        }

        @Override
        public Position[] newArray(int size) {
            return new Position[size];
        }
    };
}
