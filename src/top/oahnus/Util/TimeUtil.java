package top.oahnus.Util;

import java.util.Date;

/**
 * Created by oahnus on 2016/7/22.
 */
public class TimeUtil {
    public static String getTimeNow(){
        Date date = new Date();
        String hour = String.valueOf(date.getHours());
        String minute = String.valueOf(date.getMinutes());
        String second = String.valueOf(date.getSeconds());

        if(hour.length()<2) hour = "0"+hour;
        if(minute.length()<2) minute = "0"+minute;
        if(second.length()<2) second = "0"+second;

        return hour+":"+minute+":"+second;
    }
}
