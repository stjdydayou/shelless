package com.dliyun.platform.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * 完整时间 yyyy-MM-dd HH:mm:ss
     */
    private static final String DATA_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    private final static long MILLISECONDS_EVERY_DAY = 24L * 3600 * 1000;

    private DateUtil() {
    }

    /**
     * <p>
     * 当前日期时间
     * </p>
     *
     * @return
     */
    public static Date current() {
        return new Date();
    }

    /**
     * <p>
     * 判断指定日期是否在有效期之内。
     * </p>
     *
     * <p>
     * 若指定时间是：2011-06-01 09:12:00，有期效为 10 天。当前时间在 2011-06-11 09:12:00
     * 之后则表示已经不在有效期内。
     * </p>
     *
     * @param date   指定时间
     * @param period 有效期（天）
     * @return
     */
    public static boolean isIndate(Date date, int period) {
        return date.getTime() + MILLISECONDS_EVERY_DAY * period <= System.currentTimeMillis();
    }

    /**
     * <p>
     * 将 Date 中的时、分、秒、毫秒清零
     * </p>
     *
     * @param date
     * @return
     */
    public static Date clearTime(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * <p>
     * 在日期基础上做基于分钟的加和减，并且将时、分、秒、毫秒清零
     * </p>
     *
     * @param changeMinutes 变化的分钟数（负数表示减少，正数表示增加）
     * @return
     */
    public static Date changeMinute(int changeMinutes) {
        Date date = new Date();
        return changeMinute(date, changeMinutes);
    }

    public static Date changeMinute(Date date, int change) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, change);
        return c.getTime();
    }

    /**
     * <p>
     * 在日期基础上做基于日的加和减，并且将时、分、秒、毫秒清零
     * </p>
     *
     * @param changeDays 变化的天数（负数表示减少，正数表示增加）
     * @return
     */
    public static Date changeDay(int changeDays) {
        Date date = new Date();
        return changeDay(date, changeDays);
    }

    public static Date changeDay(Date date, int change) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, change);
        return c.getTime();
    }

    /**
     * <p>
     * 在日期基础上做基于日的加和减，并且将时、分、秒、毫秒清零
     * </p>
     *
     * @param changeMonth 变化的月数（负数表示减少，正数表示增加）
     * @return
     */
    public static Date changeMonth(int changeMonth) {
        Date date = new Date();
        return changeMonth(date, changeMonth);
    }

    public static Date changeMonth(Date date, int change) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, change);
        return c.getTime();
    }

    /**
     * <p>
     * 在日期基础上做基于日的加和减，并且将时、分、秒、毫秒清零
     * </p>
     *
     * @param change 变化的月数（负数表示减少，正数表示增加）
     * @return
     */
    public static Date changeYear(int change) {
        Date date = new Date();
        return changeYear(date, change);
    }

    public static Date changeYear(Date date, int change) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, change);
        return c.getTime();
    }

    /**
     * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getDateFormatter() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(DATA_TIME_FORMATTER);
        return df.format(date);
    }

    /**
     * <p>
     * 以 datetime 格式化日期
     * </p>
     *
     * @param date
     * @return
     */
    public static String formatDatetime(Date date) {
        if (date == null) {
            return null;
        }
        return format("yyyy-MM-dd HH:mm:ss", date);
    }

    /**
     * <p>
     * 以 datetime 格式化日期
     * </p>
     *
     * @param date
     * @return
     */
    public static String formatDatetimes(Date date) {
        if (date == null) {
            return null;
        }
        return format("HHmmssyyyyMMdd ", date);
    }

    /**
     * <p>
     * 以 date 格式化日期
     * </p>
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return format("yyyyMMdd", date);
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */

    public static int subDays(Date startDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(startDate);

        int start = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTime(endDate);

        int end = calendar.get(Calendar.DAY_OF_YEAR);

        return start - end;
    }

    public static String format(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String format(String pattern) {
        return new SimpleDateFormat(pattern).format(current());
    }

    public static Date parse(String pattern, String strDateTime) {
        Date date = null;
        if (strDateTime == null || pattern == null) {
            return null;
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            formatter.setLenient(false);
            date = formatter.parse(strDateTime);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }
}
