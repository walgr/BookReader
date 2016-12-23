package com.wpf.bookreader.Utils;

/**
 * Created by 王朋飞 on 12-22-0022.
 *
 */

public class LogTime {
    public long startTime,endTime;

    public String getUseTime() {
        return formatTime(endTime - startTime);
    }

    private String formatTime(long time) {
        if(time >= 0 && time<1000)
            return time + "毫秒";
        else if(time >= 1000 && time < 1000*60)
            return time/1000 + "秒" + formatTime(time%1000);
        else if(time >= 1000*60 && time < 1000*60*60)
            return time/(1000*60) + formatTime(time%(1000*60));
        else return "时间久的都超神了";
    }
}
