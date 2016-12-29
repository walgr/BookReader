package com.wpf.bookreader.DataBase;

import com.wpf.bookreader.BookReaderApplication;
import com.wpf.bookreader.DataBase.greendao.gen.BookInfoDao;

import java.util.List;

/**
 * Created by 王朋飞 on 12-26-0026.
 *
 */

public class BookManager {

    public static void saveBook(BookInfo bookInfo) {
        if(getBook(bookInfo.bookUrl) == null)
            BookReaderApplication.bookInfoDao.insert(bookInfo);
        else
            BookReaderApplication.bookInfoDao.update(bookInfo);
    }

    public static void delBook(String name) {
        BookReaderApplication.bookInfoDao.deleteByKey(name);
    }

    public static BookInfo getBook(String url) {
        return BookReaderApplication.bookInfoDao.queryBuilder()
                .where(BookInfoDao.Properties.BookUrl.eq(url)).unique();
    }

    public static List<BookInfo> getBookList() {
        return BookReaderApplication.bookInfoDao.queryBuilder().list();
    }

}
