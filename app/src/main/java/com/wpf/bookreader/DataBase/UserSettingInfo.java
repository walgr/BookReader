package com.wpf.bookreader.DataBase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 王朋飞 on 17:26.
 *
 */

@Entity
public class UserSettingInfo {

    @Id
    //用户ID
    private Long id;

    //配色
    private int colorPos;

    //字体
    private String fontName = "系统默认";

    //字体大小
    private int textSize;

    @Generated(hash = 1486890494)
    public UserSettingInfo(Long id, int colorPos, String fontName, int textSize) {
        this.id = id;
        this.colorPos = colorPos;
        this.fontName = fontName;
        this.textSize = textSize;
    }

    @Generated(hash = 1501928232)
    public UserSettingInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFontName() {
        return this.fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public int getTextSize() {
        return this.textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getColorPos() {
        return this.colorPos;
    }

    public void setColorPos(int colorPos) {
        this.colorPos = colorPos;
    }

}
