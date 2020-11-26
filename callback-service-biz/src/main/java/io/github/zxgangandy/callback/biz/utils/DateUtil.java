package io.github.zxgangandy.callback.biz.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final long ONE_DAY = 24 * 60 * 60 * 1000;

    private static final String DATE_FORMAT1 = "yyyy-MM-dd";
    private static final String DATE_FORMAT2 = "yyyyMMdd";
    private static final String DATETIME_FORMAT1 = "yyyy-MM-dd HH:mm:ss";
    private static final String DATETIME_FORMAT2 = "yyyyMMddHHmmss";
    private static final String DATETIME_FORMAT3 = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String DATETIME_FORMAT4 = "yyyyMMddHHmmssSSS";

    private static final String TIME_FORMAT1 = "HH:mm:ss";


    //获取当前时间(字符串格式)
    public static String getCurrentDateTime(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.now().format(formatter);
    }

    //获取昨天时间格式
    public static String getYesterdayByFormat(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.now().minusDays(1).format(formatter);
    }

    //获取从1970年1月1日到现在的秒数
    public static Long getSecond(String dateTime, String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dtf);
        Long second = localDateTime.toEpochSecond(ZoneOffset.of("+8"));
        return second;
    }

    //LocalDateTime转String
    public static String getLocalDateTimeToStr(LocalDateTime localDateTime, String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(dtf);
    }

    //获取从1970年1月1日到现在的秒数转LocalDateTime
    public static LocalDateTime getSecondToLocalDateTime(Long second) {
        Instant instant = Instant.ofEpochSecond(second);
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    //获取从1970年1月1日到现在的秒数转字符串格式
    public static String getSecondToStr(long second, String format) {
        return getLocalDateTimeToStr(getSecondToLocalDateTime(second), format);
    }


}
