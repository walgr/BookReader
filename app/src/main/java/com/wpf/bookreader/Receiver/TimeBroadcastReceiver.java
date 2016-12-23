package com.wpf.bookreader.Receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 王朋飞 on 12-2-0002.
 * 时间监听
 */

public class TimeBroadcastReceiver extends BroadcastReceiver {

    private OnTimeChangeListener onTimeChangeListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
            if(onTimeChangeListener != null)
                onTimeChangeListener.timeChange(getTime());
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String getTime() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    public void setOnTimeChangeListener(OnTimeChangeListener onTimeChangeListener) {
        this.onTimeChangeListener = onTimeChangeListener;
    }

    public interface OnTimeChangeListener {
        void timeChange(String time);
    }
}
