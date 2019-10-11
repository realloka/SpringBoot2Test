package com.plantynet.common.util.lang;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

public final class DateUtil
{
    private DateUtil()
    {
        throw new AssertionError();
        
        /*     
        //JDK8
        LocalDateTime timePoint = LocalDateTime.now();
        //포맷변환
        timePoint.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        //다음날
        timePoint.plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        //3시간 전
        timePoint.minusHours(3).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        
        String date = "16/08/2016";
        
        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);
        
        //epoch
        LocalDateTime dateTimePoint = toLocalDateTime("2010-11-25 09:00", "yyyy-MM-dd HH:mm");
        System.out.println(dateTimePoint);//2010-11-25T09:00
        System.out.println(Date.from(dateTimePoint.atZone(ZoneId.systemDefault()).toInstant()));//Thu Nov 25 09:00:00 KST 2010
        System.out.println(toEpochSecond("2010-11-25 09:00", "yyyy-MM-dd HH:mm"));//1290643200
        
        LocalDate datePoint = toLocalDate("2010-11-25", "yyyy-MM-dd");
        System.out.println(datePoint);//2010-11-25
        System.out.println(toEpochSecond("2010-11-25", "yyyy-MM-dd"));//1290643200
        */
    }
    
    /**
     * 현재 날짜 구하기
     * @param format(기본값:yyyy-MM-dd)
     * @return
     */
    public static String getToday(String formatStr)
    {
        
        LocalDate timePoint = LocalDate.now();
        if(StringUtil.isEmptyOrNull(formatStr))
        {
            formatStr = "yyyy-MM-dd";
        }
        
        return timePoint.format(DateTimeFormatter.ofPattern(formatStr));
    }
    
    /**
     * 현재 날짜 시간 구하기
     * @param format(기본값:yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public static String getTodayDateTime(String formatStr)
    {
        LocalDateTime timePoint = LocalDateTime.now();
        if(StringUtil.isEmptyOrNull(formatStr))
        {
            formatStr = "yyyy-MM-dd HH:mm:ss";
        }
        
        return timePoint.format(DateTimeFormatter.ofPattern(formatStr));
    }
    
    /**
     * 이전 날짜 구하기
     * @param dateTimeStr
     * @param days
     * @param formatStr(기본값:yyyy-MM-dd)
     * @return
     */
    public static String getPrevDate(String dateTimeStr, int days, String formatStr)
    {
        if (days < 0) { return dateTimeStr; }
        if(StringUtil.isEmptyOrNull(formatStr))
        {
            formatStr = "yyyy-MM-dd";
        }
        
        if(dateTimeStr.length() != formatStr.length())
        {
            return dateTimeStr;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatStr);
        
        LocalDate timePoint = LocalDate.parse(dateTimeStr, formatter);
        
        return timePoint.minusDays(days).format(formatter);
    }
    
    /**
     * 이후 날짜 구하기
     * @param dateTimeStr
     * @param days
     * @param formatStr(기본값:yyyy-MM-dd)
     * @return
     */
    public static String getNextDate(String dateTimeStr, int days, String formatStr)
    {
        if (days < 0) { return dateTimeStr; }
        if(StringUtil.isEmptyOrNull(formatStr))
        {
            formatStr = "yyyy-MM-dd";
        }
        
        if(dateTimeStr.length() != formatStr.length())
        {
            return dateTimeStr;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatStr);
        
        LocalDate timePoint = LocalDate.parse(dateTimeStr, formatter);
        
        return timePoint.plusDays(days).format(formatter);
    }
    
    /**
     * 이전 주 날짜 구하기
     * @param dateTimeStr
     * @param weeks
     * @param formatStr(기본값:yyyy-MM-dd)
     * @return
     */
    public static String getPrevWeekDate(String dateTimeStr, int weeks, String formatStr)
    {
        if (weeks < 0) { return dateTimeStr; }
        
        if(StringUtil.isEmptyOrNull(formatStr))
        {
            formatStr = "yyyy-MM-dd";
        }
        
        if(dateTimeStr.length() != formatStr.length())
        {
            return dateTimeStr;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatStr);
        
        LocalDate timePoint = LocalDate.parse(dateTimeStr, formatter);
        
        return timePoint.minusWeeks(weeks).format(formatter);
    }
    
    /**
     * 다음 주 날짜 구하기
     * @param dateTimeStr
     * @param weeks
     * @param formatStr(기본값:yyyy-MM-dd)
     * @return
     */
    public static String getNextWeekDate(String dateTimeStr, int weeks, String formatStr)
    {
        if (weeks < 0) { return dateTimeStr; }
        
        if(StringUtil.isEmptyOrNull(formatStr))
        {
            formatStr = "yyyy-MM-dd";
        }
        
        if(dateTimeStr.length() != formatStr.length())
        {
            return dateTimeStr;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatStr);
        
        LocalDate timePoint = LocalDate.parse(dateTimeStr, formatter);
        
        return timePoint.plusWeeks(weeks).format(formatter);
    }
    
    /**
     * 이전 달 날짜 구하기
     * @param dateTimeStr
     * @param months
     * @param formatStr(기본값:yyyy-MM-dd)
     * @return
     */
    public static String getPrevMonthDate(String dateTimeStr, int months, String formatStr)
    {
        if (months < 0) { return dateTimeStr; }
        
        if(StringUtil.isEmptyOrNull(formatStr))
        {
            formatStr = "yyyy-MM-dd";
        }
        
        if(dateTimeStr.length() != formatStr.length())
        {
            return dateTimeStr;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatStr);
        
        LocalDate timePoint = LocalDate.parse(dateTimeStr, formatter);
        
        return timePoint.minusMonths(months).format(formatter);
    }
    
    /**
     * 다음 달 날짜 구하기
     * @param dateTimeStr
     * @param months
     * @param formatStr(기본값:yyyy-MM-dd)
     * @return
     */
    public static String getNextMonthDate(String dateTimeStr, int months, String formatStr)
    {
        if (months < 0) { return dateTimeStr; }
        
        if(StringUtil.isEmptyOrNull(formatStr))
        {
            formatStr = "yyyy-MM-dd";
        }
        
        if(dateTimeStr.length() != formatStr.length())
        {
            return dateTimeStr;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatStr);
        
        LocalDate timePoint = LocalDate.parse(dateTimeStr, formatter);
        
        return timePoint.plusMonths(months).format(formatter);
    }
    
    /**
     * 각 달의 1일의 요일을 구함(일요일:0, ...)
     * @param year
     * @param month
     * @return
     */
    public static int getFirstDayWeek(int year, int month)
    {
        LocalDate timePoint = LocalDate.of(year, month, 1);
        return timePoint.getDayOfWeek().getValue();
    }
    
    /**
     * 입력된 일자를 Calendar 객체로 반환한다.
     * @param argDate
     *            변환할 일자( 1998.01.02, 98.01.02, 19980102, 980102 등 )
     * @return 해당일자에 해당하는 Calendar
     */
    public static Calendar toCalendar(String fmtDate)
    {
        String date = getUnFmtDateString(fmtDate);
        
        GregorianCalendar calendar = new GregorianCalendar();
        
        calendar.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1, Integer.parseInt(date.substring(6, 8)));
        
        return calendar;
    }
    
    /**
     * 구분자가 제거된 날짜 스트링
     * @param fmtDate
     * @return
     */
    public static String getUnFmtDateString(String fmtDate)
    {
        boolean isCharater = false;
        boolean isCorrect = true;
        
        String strDate = "";
        String date = "";
        String result = "";
        
        if (fmtDate != null)
        {
            strDate = fmtDate.trim();
        }
        
        for (int inx = 0; inx < strDate.length(); inx++)
        {
            if (Character.isLetter(strDate.charAt(inx)) || strDate.charAt(inx) == ' ')
            {
                isCorrect = false;
                
                break;
            }
        }
        
        if (!isCorrect) { return ""; }
        
        if (strDate.length() != 8)
        {
            if (strDate.length() != 6 && strDate.length() != 10) { return ""; }
            
            if (strDate.length() == 6)
            {
                if (Integer.parseInt(strDate.substring(0, 2)) > 50)
                {
                    date = "19";
                }
                else
                {
                    date = "20";
                }
                
                result = date + strDate;
            }
            
            if (strDate.length() == 10)
            {
                result = strDate.substring(0, 4) + strDate.substring(4, 8) + strDate.substring(8, 10);
            }
        }
        else
        {
            try
            {
                Integer.parseInt(strDate);
            }
            catch (NumberFormatException ne)
            {
                isCharater = true;
            }
            
            if (isCharater)
            {
                date = strDate.substring(0, 2) + strDate.substring(3, 5) + strDate.substring(6, 8);
                
                if (Integer.parseInt(strDate.substring(0, 2)) > 50)
                {
                    result = "19" + date;
                }
                else
                {
                    result = "20" + date;
                }
            }
            else
            {
                return strDate;
            }
        }
        
        return result;
    }
    
    /**
     * 입력된 일자를 LocalDateTime으로 변경
     * @param dateTimeStr
     * @param format(기본값:yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public static LocalDateTime toLocalDateTime(String dateTimeStr, String formatStr)
    {
        if(StringUtil.isEmptyOrNull(formatStr))
        {
            formatStr = "yyyy-MM-dd HH:mm:ss";
        }
        
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(formatStr));
    }
    
    /**
     * 입력된 일자를 LocalDate로 변경
     * @param dateTimeStr
     * @param format(기본값:yyyy-MM-dd)
     * @return
     */
    public static LocalDate toLocalDate(String dateTimeStr, String formatStr)
    {
        if(StringUtil.isEmptyOrNull(formatStr))
        {
            formatStr = "yyyy-MM-dd";
        }
        
        return LocalDate.parse(dateTimeStr, DateTimeFormatter.ofPattern(formatStr));
    }
    
    /**
     * 입력된 일자를 epoch time으로 변경 
     * @param dateTimeStr
     * @param format(기본값:yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public static long toEpochSecond(String dateTimeStr, String formatStr)
    {
        if(StringUtil.isEmptyOrNull(formatStr))
        {
            formatStr = "yyyy-MM-dd HH:mm:ss";
        }
        
        if(dateTimeStr.length() != formatStr.length())
        {
            return -1;
        }
        
        if(formatStr.indexOf("H") > -1 || formatStr.indexOf("h") > -1 || formatStr.indexOf("K") > -1 || formatStr.indexOf("k") > -1)
        {//하나라도 있다... 
            LocalDateTime dateTimePoint = toLocalDateTime(dateTimeStr, formatStr);
            return dateTimePoint.atZone(ZoneId.systemDefault()).toEpochSecond();
        }
        else 
        {//한개도 없다
            LocalDate datePoint = toLocalDate(dateTimeStr, formatStr);
            LocalDateTime dateTimePoint = datePoint.atStartOfDay();
            return dateTimePoint.atZone(ZoneId.systemDefault()).toEpochSecond();
        }
    }
}