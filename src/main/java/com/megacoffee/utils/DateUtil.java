package com.megacoffee.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static String pattern = "yyyy-MM-dd";

	public static Date toDate(int year, int month, int date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DATE, date);

		return calendar.getTime();
	}
	
	/**
	 * 현재 시간을 반환한다.
	 * @return
	 */
	public static Date toDate()
	{
		return Calendar.getInstance().getTime();
	}

	/**
	 * yyyy-MM-dd 형태의 문자형 날짜를 Date로 변환한다.
	 * @param value
	 * @return
	 */
	public static Date toDate(String value)
	{
		return toDate(value, "yyyy-MM-dd");
	}

	/**
	 * format 형태의 문자형 날짜를 Date로 변환한다.
	 * @param value
	 * @param format
	 * @return
	 */
	public static Date toDate(String value, String format)
	{
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try
		{
			d = sdf.parse(value);
		}
		catch(ParseException e)
		{

		}
		catch(NullPointerException e)
		{
			
		}

		return d;
	}

	/**
	 * value 값이 없으면 현재 시간으로 반환한다.
	 * @param value
	 * @return
	 */
	public static Date nvl(Date value)
	{
		return nvl(value, toDate());
	}

	/**
	 * value 값이 없으면 replace 를 반환한다.
	 * @param value
	 * @param replace
	 * @return
	 */
	public static Date nvl(Date value, Date replace)
	{
		return value == null ? replace : value;
	}
	
	/**
	 * 현재 년도를 4자리로 반환한다.
	 * @return
	 */
	public static String toStringYear()
	{
		return toStr(toDate(), "yyyy");
	}
	
	/**
	 * 현재 월을 2자리로 반환한다.
	 * @return
	 */
	public static String toStringMonth()
	{
		return toStr(toDate(), "MM");
	}
	
	/**
	 * 현재 일을 2자리로 반환한다.
	 * @return
	 */
	public static String toStringDate()
	{
		return toStr(toDate(), "dd");
	}
	
	/**
	 * 현재 년도를 반환한다.
	 * @return
	 */
	public static int toIntYear()
	{
		return toIntYear(toDate());
	}

	public static int toIntYear(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 현재 월을 반환한다.
	 * @return
	 */
	public static int toIntMonth()
	{
		return toIntMonth(toDate());
	}

	public static int toIntMonth(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.MONTH)+1;
	}
	
	/**
	 * 현재 일을 반환한다.
	 * @return
	 */
	public static int toIntDate()
	{
		return toIntMonth(toDate());
	}

	public static int toIntDate(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.DATE);
	}
	
	/**
	 * 현재 요일을 반환한다.
	 * @return
	 */
	public static int toIntDateOfWeek()
	{
		return toIntDateOfWeek(toDate());
	}

	public static int toIntDateOfWeek(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 현재 시간을 문자형태로 반환한다.
	 * 예) 2016-09-15
	 * @return
	 */
	public static String toStr()
	{
		return toStr(toDate());
	}
	/**
	 * date를 yyyy-MM-dd(예 '2016-09-15') 형태의 문자로 변환한다.
	 * @param value
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static String toStr(Date date)
	{
		return toStr(date, pattern);
	}
	
	/**
	 * date를 format 값에 맞는 형태의 문자로 변환한다.
	 * @param value
	 * @param format
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static String toStr(Date date, String format)
	{
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * format 값에 맞는 현재 날짜를 반환한다.
	 * @param format
	 * @return
	 */
	public static String toStr(String format)
	{
		return toStr(toDate(), format);
	}

	/**
	 * 오늘을 기준하여 value를 더한 날짜를 반환한다.
	 * @param value
	 * @return
	 */
	public static Date addDate(int value)
	{
		return addDate(toDate(), value);
	}
	/**
	 * date를 기준하여 value를 더한 날짜를 반환한다.
	 * @param date
	 * @param value
	 * @return
	 */
	public static Date addDate(Date date, int value)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, value);
		
		return calendar.getTime();
	}

	public static Date setDate(Date date, int value)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, value);
		
		return calendar.getTime();
	}

	public static Date addMonth(Date date, int value)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, value);
		
		return calendar.getTime();
	}
	
	/**
	 * date1과 date2의 날짜 차이
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int datediff(Date date1, Date date2)
	{
		long l = Math.max(date1.getTime(), date2.getTime()) - Math.min(date1.getTime(), date2.getTime());

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(l);
		
		return c.get(Calendar.DAY_OF_YEAR);
	}

	public static int dateDiffYear(Date date1, Date date2)
	{
		int year1 = DateUtil.toIntYear(date1);
		int month1 = DateUtil.toIntMonth(date1);
		int day1 = DateUtil.toIntDate(date1);
		int year2 = DateUtil.toIntYear(date2);
		int month2 = DateUtil.toIntMonth(date2);
		int day2 = DateUtil.toIntDate(date2);
		
		int i = 0;
		i = year1 - year2;
		i += (month1 < month2 || (month1 == month2 && day1 < day2)) ? -1 : 0;

		return i;
	}
	
	/**
	 * 현재의 날짜와 시간을 반환한다.
	 * 예) 20160915132259000
	 * @return
	 */
	public static String toStrTimestamp()
	{
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}

	/**
	 * date가 속한 주의 일요일을 반환
	 * @param date
	 * @return
	 */
	public static Date sundayOfWeek(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	
		return c.getTime();
	}
	
	/**
	 * Date가 속한 주의 토요일을 반환
	 * @param date
	 * @return
	 */
	public static Date saturdayOfWeek(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
	
		return c.getTime();
	}
	
	/**
	 * date가 속한 월의 첫번째 일요일을 반환
	 * @param date
	 * @return
	 */
	public static Date firstSundayOfMonth(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, 1);
	
		return sundayOfWeek(c.getTime()) ;
	}
	
	/**
	 * date가 속한 월의 마지막 토요일을 반환
	 * @param date
	 * @return
	 */
	public static Date lastSaturdayOfMonth(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, 1);
		c.add(Calendar.MONDAY, 1);
		c.add(Calendar.DATE, -1);

		return saturdayOfWeek(c.getTime()) ;
	}

	/**
	 * 현재 날짜를 yyyy-MM-dd 형식으로 반환
	 */
	public static String getNowDate() {
		return getNowDate("yyyy-MM-dd");
	}

	public static String getNowDate(String format) {
		if(format == null){
			format = "yyyyMMdd";
		}	
        return LocalDate.now().format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * 현재 일시를 yyyy-MM-dd HH:mm:ss 형식으로 반환
	 */
	public static String getNowDatetime(){
		return getNowDatetime("yyyy-MM-dd HH:mm:ss");
	}

	public static String getNowDatetime(String format){
		if(format == null){
			format = "yyyyMMddHHmmss";
		}	
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }
}
