package com.wpf.bookreader.Utils;


import android.content.Context;

import java.util.List;

/**
 * Created by 王朋飞 on 12-28-0028.
 *
 */

public class Tools {

    public static boolean isEmpty(List<String> list) {
        return list == null || list.isEmpty();
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
