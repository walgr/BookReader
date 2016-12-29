package com.wpf.bookreader.Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王朋飞 on 11-30-0030.
 * 获取字体
 */

public class FontsListManager {

    public static List<Typeface> getFontList(@NonNull Context context) {
        List<Typeface> typefaceList = new ArrayList<>();
        String[] fontNameList =  getAssetList(context,"fonts");
        assert fontNameList != null;
        for(String name : fontNameList) {
            typefaceList.add(Typeface.createFromAsset(context.getAssets(),"fonts\\"+name));
        }
        return typefaceList;
    }

    public static String[] getAssetList(@NonNull Context context, @NonNull String pathName) {
        try {
            return context.getAssets().list(pathName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    public static InputStream getAssets(@NonNull Context context, @NonNull String Type, @NonNull String pathName) {
        AssetManager assetManager = context.getAssets();
        try {
            return assetManager.open(pathName+"/" + Type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
