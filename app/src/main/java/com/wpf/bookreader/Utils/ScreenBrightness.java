package com.wpf.bookreader.Utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 王朋飞 on 11-30-0030.
 * 屏幕亮度
 */

public class ScreenBrightness {

    public static boolean isAutoScreenBrightness(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void closeAutoScreenBrightness(AppCompatActivity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Settings.System.canWrite(context)) {
            return;
        }
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    public static void openAutoScreenBrightness(AppCompatActivity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Settings.System.canWrite(context)) {
            return;
        }
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    public static void setAutoScreenBrightness(AppCompatActivity context, boolean open) {
        if(open) openAutoScreenBrightness(context);
        else closeAutoScreenBrightness(context);
    }

    public static int getScreenBrightness(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return 255;
    }

    public static void setScreenBrightness(Context context,int systemBrightness) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS,systemBrightness);
    }

}
