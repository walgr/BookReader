package com.wpf.bookreader.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 王朋飞 on 12-1-0001.
 * 电量监控
 */

public class BatteryBroadcastReceiver extends BroadcastReceiver {

    private int oldBattery = 0;
    private OnBatteryChangeListener onBatteryChangeListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
            //得到系统当前电量
            int level=intent.getIntExtra("level", 0);
            //取得系统总电量
            int total=intent.getIntExtra("scale", 100);
            int battery = (level*100)/total;

            if(battery != oldBattery) {
                if(onBatteryChangeListener != null) onBatteryChangeListener.batteryChange(battery);
                oldBattery = battery;
            }
        }
    }

    public void setOnBatteryChangeListener(OnBatteryChangeListener onBatteryChangeListener) {
        this.onBatteryChangeListener = onBatteryChangeListener;
    }

    public interface OnBatteryChangeListener {
        void batteryChange(int battery);
    }
}
