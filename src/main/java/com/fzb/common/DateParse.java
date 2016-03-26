package com.fzb.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateParse {

    private static DateParse instance = new DateParse();

    private DateParse() {
    }

    public static DateParse getInstance() {
        return instance;
    }

    public Date setDate(ResultSet rs, String index) {
        try {
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
            Format f = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            Date d = (Date) f.parseObject(sdfDate.format(rs.getDate(index))
                    + "-" + sdfTime.format(rs.getTime(index)));
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }


    public String getDate(Date date) {
        return "";
    }

    @SuppressWarnings("deprecation")
    public String toISO8601(Date releaseTime) {
        return (releaseTime.getYear() + 1900) + "" + (releaseTime.getMonth() + 1) + "" + (releaseTime.getDate()) + "T" + releaseTime.getHours() + releaseTime.getMinutes() + releaseTime.getSeconds() + "+08";
    }

    public Date getDateByStr(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return (Date) sdf.parseObject(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String simpleDateMsg(Date date) {
        System.out.println(date.getTime());
        long cdn = (Calendar.getInstance().getTimeInMillis() - date.getTime()) / 1000;
        if (cdn < 60) {
            return "刚刚";
        } else if (cdn <= 60 * 60 && cdn > 60) {
            return cdn / 60 + "分钟前";
        } else if (cdn > 3600 && cdn <= 86400) {
            return cdn / 3600 + "小时前";
        } else if (cdn > 86400 && cdn <= 2592000) {
            if (cdn / 86400 < 2) {
                return "昨天";
            }
            return cdn / 86400 + "天前";
        } else if (cdn > 2592000 && cdn <= 31104000) {
            return cdn / 2592000 + "月前";
        } else if (cdn > 31104000) {
            return cdn / 31104000 + "年前";
        }
        return "error";
    }

}
