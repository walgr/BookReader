package com.wpf.bookreader.DataBase;

import com.wpf.bookreader.DataBase.greendao.gen.UserSettingInfoDao;

import static com.wpf.bookreader.BookReaderApplication.userSettingInfoDao;

/**
 * Created by 王朋飞 on 12-28-0028.
 *
 */

public class UserSettingManager {

    public static void saveUserSettingInfo(UserSettingInfo userSettingInfo) {
        if(getUserSettingInfo(userSettingInfo.getId()) == null)
            userSettingInfoDao.insert(userSettingInfo);
        else
            userSettingInfoDao.update(userSettingInfo);
    }

    public void delUserSettingInfo(Long id) {
        userSettingInfoDao.deleteByKey(id);
    }

    public static UserSettingInfo getUserSettingInfo(Long id) {
        return userSettingInfoDao.queryBuilder()
                .where(UserSettingInfoDao.Properties.Id.eq(id)).unique();
    }
}
