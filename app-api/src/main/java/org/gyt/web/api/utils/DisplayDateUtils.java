package org.gyt.web.api.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换工具
 * Created by y27chen on 2016/10/18.
 */
@Component
public class DisplayDateUtils {

    public String getDisplayDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long delta = System.currentTimeMillis() - date.getTime();

        if (delta > 0) {
            long min = delta / 1000 / 60;

            if (min <= 5) {
                return "刚刚";
            }

            long hours = min / 60;

            if (hours < 1) {
                return String.format("%d 分钟前", (int) Math.ceil(min));
            }

            long days = hours / 24;

            if (days < 1) {
                return String.format("%d 小时前", (int) Math.ceil(hours));
            }

            long week = days / 7;

            if (week < 1) {
                return String.format("%d 天前", (int) Math.ceil(days));
            } else if (week < 2) {
                return "一周前";
            }
        }

        return simpleDateFormat.format(date);
    }
}
