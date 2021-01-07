package com.demo.service.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

    public static final ZoneId UTC8 = ZoneId.of("UTC+8");
    public static final String WORK_DATE_FMT = "yyyy-MM-dd";
    public static final String SEQ_FORMAT = "yyyyMMddHHmmss";
    public static final String TX_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String ONLY_TIME_FMT = "HH:mm:ss";

    private DateUtil(){
        //do nothing
    }


    public static String getSeqDate(){
        return getDateStrWithFormat(SEQ_FORMAT);
    }

    public static String getNowDateTime(){
        return getDateStrWithFormat(TX_TIMESTAMP_FORMAT);
    }


    public static String dateToStr(Date date, String fmt){
        Instant instant = date.toInstant();
        return instant.atZone(UTC8).toLocalDateTime().format(DateTimeFormatter.ofPattern(fmt));
    }

    public static String getDateStrWithFormat(String format){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static LocalDateTime dateToLocalDateTime(Date date){
        Instant instant = date.toInstant();
        return instant.atZone(UTC8).toLocalDateTime();
    }

    public static Date gateNow(){
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = localDateTime.atZone(UTC8);
        return Date.from(zdt.toInstant());
    }

    public static LocalDateTime stringToLocalDateTime(String str, String fmt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(fmt);
        return LocalDateTime.parse(str, formatter);
    }

    public static LocalDate stringToLocalDate(String str, String fmt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(fmt);
        return LocalDate.parse(str, formatter);
    }

    public static Calendar stringToCalendar(String str, String fmt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(fmt);
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        return localDateTimeToCalendar(dateTime);
    }

    public static Calendar localDateTimeToCalendar(LocalDateTime localDateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(localDateTime.getYear(), localDateTime.getMonthValue()-1, localDateTime.getDayOfMonth(),
                     localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
        return calendar;
    }
}