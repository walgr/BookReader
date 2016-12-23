package com.wpf.bookreader.Utils;

import com.wpf.bookreader.DataInfo.BookInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王朋飞 on 12-19-0019.
 * 获取用户书籍
 */

public class GetBookList {

    public static List<BookInfo> getUserBook() {
        List<BookInfo> bookInfoList = new ArrayList<>();
        bookInfoList.add(new BookInfo()
                .setBookName("太古神王")
                .setBookImgUrl("http://www.qu.la/files/article/image/4/4140/4140s.jpg")
                .setBookUrl("http://m.qu.la/booklist/4140.html"));
        return bookInfoList;
    }
}
