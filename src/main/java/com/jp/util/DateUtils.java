package com.jp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 操作日期的工具类
 */
public class DateUtils {

	/**
	 * 当前时间的毫秒数
	 */
	static long now = System.currentTimeMillis();

	/**
	 * 当前日期
	 */
	public static Date CurrTime = new Date(now);

	/**
	 * 日期
	 */
	private static Date date = new Date();

	/**
	 * 构造函数
	 */
	public DateUtils() {
	}

	public static long getNow() {
		CurrTime = new java.util.Date();
		return CurrTime.getTime();
	}

	// 获取某年某月的所以周末的日期
	public static List getMonthWeekend(int year, int month) {
		int num = 0;
		List weekendList = new ArrayList();
		String week = null;
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getDefault());
		cal.clear();
		int dateNum = getMonthDay(year, month);
		for (int date = 1; date <= dateNum; date++) {

			cal.set(year, month - 1, date);
			num = cal.get(cal.DAY_OF_WEEK);
			if (num == 1 || num == 7) {
				weekendList.add(new Integer(date));
			}
		}

		return weekendList;
	}

	/**
	 * 获取beginDate开始的日期到endDate连续的日期
	 */
	public static List getDateFromToList(String beginDate, String endDate) {
		List listDate = new ArrayList();
		Date sDate = getDateBy(beginDate);
		Date eDate = getDateBy(endDate);
		// 将时间格式化为YYYY-MM-DD
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		while (!sDate.after(eDate)) {
			listDate.add(dateformat.format(sDate));
			sDate = BtoEDate(sDate, 1);
		}
		return listDate;
	}

	public static Date DateAdd(Date date, int day) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, day);
		date = calendar.getTime();
		return date;
	}
	
	public static Date DateMinute(Date date, int minute) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.MINUTE, minute);
		date = calendar.getTime();
		return date;
	}

	private static Date getDateBy(String date) {
		String[] end = date.split(" ")[0].split("-");
		Date eDate = new Date(Integer.parseInt(end[0]) - 1900, Integer.parseInt(end[1]) - 1, Integer.parseInt(end[2]));

		return eDate;
	}

	private static Date BtoEDate(Date date, int len) {
		if (len != 0) {
			if (len > 0) {
				for (int i = 0; i < len; i++) {
					date = DateAdd(date);
				}
			} else if (len < 0) {
				for (int i = 0; i > len; i--) {
					date = DatePro(date);
				}
			}
		}
		return date;
	}

	private static Date DateAdd(Date date) {
		Calendar cal = GetCalendar(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		day++;

		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
			if (day == 32) {
				day = 1;
				month++;
			}
		if (month == 4 || month == 6 || month == 9 || month == 11)
			if (day == 31) {
				day = 1;
				month++;
			}
		if (month == 2)
			if (CheckRun(year)) {
				if (day == 30) {
					day = 1;
					month++;
				}
			} else {
				if (day == 29) {
					day = 1;
					month++;
				}
			}
		if (month == 13) {
			month = 1;
			year++;
		}
		cal.set(year, month - 1, day);
		return cal.getTime();
	}

	private static Date DatePro(Date date) {
		Calendar cal = GetCalendar(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		day--;

		if (day == 0) {
			month--;
			if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10)
				day = 31;
			else if (month == 4 || month == 6 || month == 9 || month == 11)
				day = 30;
			else if (month == 2)
				if (CheckRun(year)) {
					day = 29;
				} else {
					day = 28;
				}
			else if (month == 0) {
				month = 12;
				day = 31;
				year--;
			}
		}
		cal.set(year, month - 1, day);
		return cal.getTime();
	}

	private static Calendar GetCalendar(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal;
	}

	private static boolean CheckRun(int year) {
		if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
			return true;
		} else {
			return false;
		}
	}

	// /////////////////////////////////////////////////////////////////////////////////
	/**
	 * 判断是否是闰年
	 */
	public static boolean RunNian(int The_Year) {
		if ((The_Year % 400 == 0) || ((The_Year % 4 == 0) && (The_Year % 100 != 0)))
			return true;
		else
			return false;
	}

	/**
	 * 获取某年某月的天数
	 */
	public static int getMonthDay(int the_Year, int the_Month) {
		int month_Day = 0;
		String showMonth = "";
		switch (the_Month) {
		case 1:
			showMonth = "一月";
			month_Day = 31;
			break;
		case 2:
			showMonth = "二月";
			if (RunNian(the_Year))
				month_Day = 29;
			else
				month_Day = 28;
			break;
		case 3:
			showMonth = "三月";
			month_Day = 31;
			break;
		case 4:
			showMonth = "四月";
			month_Day = 30;
			break;
		case 5:
			showMonth = "五月";
			month_Day = 31;
			break;
		case 6:
			showMonth = "六月";
			month_Day = 30;
			break;
		case 7:
			showMonth = "七月";
			month_Day = 31;
			break;
		case 8:
			showMonth = "八月";
			month_Day = 31;
			break;
		case 9:
			showMonth = "九月";
			month_Day = 30;
			break;
		case 10:
			showMonth = "十月";
			month_Day = 31;
			break;
		case 11:
			showMonth = "十一月";
			month_Day = 30;
			break;
		case 12:
			showMonth = "十二月";
			month_Day = 31;
			break;

		}

		return month_Day;
	}

	/**
	 * 通过字符串HH:mm:ss得到毫秒值
	 * 
	 * 
	 * @param s
	 *            String 格式为HH:mm:ss的字符串
	 * @return long 转换后的毫秒值
	 */
	public static long getTimebyString(String s) {
		int i = s.indexOf(":");
		int j = s.lastIndexOf(":");
		long time = 0;
		if ((i != -1) && (j != -1)) {
			int hh = Integer.parseInt(s.substring(0, i));
			int mm = Integer.parseInt(s.substring(i + 1, j));
			int ss = Integer.parseInt(s.substring(j + 1, s.length()));
			time = hh * 60 * 60 * 1000 + mm * 60 * 1000 + ss * 1000;
		}
		return time;
	}

	/**
	 * 通过字符串HH:mm得到毫秒值
	 * 
	 * 
	 * @param s
	 *            String 格式为HH:mm的字符串
	 * @return long 转换后的毫秒值
	 */
	public static long getTimesbyString(String s) {
		int i = s.indexOf(":");
		long time = 0;
		if ((i != -1)) {
			int hh = Integer.parseInt(s.substring(0, i));
			int mm = Integer.parseInt(s.substring(i + 1, s.length()));
			time = hh * 60 * 60 * 1000 + mm * 60 * 1000;
		}
		return time;
	}

	/**
	 * String 格式为HH:mm的字符串 单位毫秒 根据2个时间计算时间差
	 */
	public static long getTimeDifference(String begintime, String endtime) {
		long begintimelong = getTimesbyString(begintime);
		long endtimelong = getTimesbyString(endtime);
		if (begintimelong >= endtimelong) {
			return 0;
		} else {
			return endtimelong - begintimelong;
		}
	}

	/**
	 * 两个日期相差天数
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int getIntervalDays(Date fDate, Date oDate) {

		if (null == fDate || null == oDate) {

			return -1;

		}

		long intervalMilli = oDate.getTime() - fDate.getTime();

		return (int) (intervalMilli / (24 * 60 * 60 * 1000));

	}

	/**
	 * 格式化日期
	 * 
	 * 
	 * @param date
	 *            Date 日期
	 * @param string
	 *            String 格式字符串
	 * 
	 * @return String 格式化后日期
	 */
	public static String FormatDate(Date date, String string) {
		SimpleDateFormat dateformat = new SimpleDateFormat(string);
		return dateformat.format(date);
	}

	/**
	 * 取得当前时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 */
	public static String getCurrTime() {
		java.util.Date date_time = new Date();
		return FormatDate(date_time, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 取得当前时间格式yyyy-MM-dd HH:mm:ss:millis
	 * 
	 * @return String
	 */
	public static String getCurrMillisTime() {
		long l = System.currentTimeMillis();
		String d = DateUtils.getCurrTime();
		return d + ":" + (l - DateUtils.getTimeInMillis(d, "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 取得当前时间格式yyyy-MM-dd HH:mm
	 * 
	 * @return String
	 */
	public static String getCurrShortTime() {
		Date date = new Date();
		return FormatDate(date, "yyyy-MM-dd HH:mm");
	}

	/**
	 * 取得当前时间格式yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String getCurrDate() {
		CurrTime = new java.util.Date();
		return FormatDate(CurrTime, "yyyy-MM-dd");
	}

	public static String getYYMMDDHHMMSS() {
		return FormatDate(new java.util.Date(), "yyMMddHHmmss");
	}

	/**
	 * 取得当前年份
	 * 
	 * @return String
	 */
	public static String getCurrYear() {
		CurrTime = new java.util.Date();
		return FormatDate(CurrTime, "yyyy");

	}

	/**
	 * 取得当前月份
	 * 
	 * @return String
	 */
	public static String getCurrMonth() {
		CurrTime = new java.util.Date();
		return FormatDate(CurrTime, "MM");

	}

	/**
	 * 取得当前日
	 * 
	 * 
	 * @return String
	 */
	public static String getCurrDay() {
		CurrTime = new java.util.Date();
		return FormatDate(CurrTime, "dd");

	}

	/**
	 * 取得当前小时
	 * 
	 * @return String
	 */
	public static String getCurrHours() {
		CurrTime = new java.util.Date();
		return FormatDate(CurrTime, "HH");
	}

	/**
	 * 取得当前分钟
	 * 
	 * @return String
	 */
	public static String getCurrMinutes() {
		CurrTime = new java.util.Date();
		return FormatDate(CurrTime, "mm");
	}

	/**
	 * 取得当前秒
	 * 
	 * 
	 * @return String
	 */
	public static String getCurrSeconds() {
		CurrTime = new java.util.Date();
		return FormatDate(CurrTime, "ss");
	}

	/**
	 * 转换日期格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            Date 日期
	 * @return String
	 */
	public static String dayToString(Date date) {
		return FormatDate(date, "yyyy-MM-dd");
	}

	/**
	 * 转换日期格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            Date 日期
	 * @return String
	 */
	public static String dateToString(Date date) {
		return FormatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 转换日期格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            Date 日期
	 * @return String
	 */
	public static String dateToString(java.sql.Date date) {
		return FormatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 转换日期格式yyyy-MM-dd HH
	 * 
	 * @param date
	 *            Date 日期
	 * @return String
	 */
	public static String dateToString1(java.sql.Date date) {
		return FormatDate(date, "yyyy-MM-dd");
	}

	/**
	 * 转换日期格式yyyy-MM-dd HH:mm
	 * 
	 * @param date
	 *            Date 日期
	 * @return String
	 */
	public static String dateToShortString(Date date) {
		return FormatDate(date, "yyyy-MM-dd HH:mm");
	}

	/**
	 * 说明：由时间格式的字符串获得年数
	 * 
	 * @param dateString
	 *            时间格式字符串
	 * 
	 * @return
	 */
	public static int getYearByString(String dateString) {
		int year = 0;
		int i = dateString.indexOf("-");
		int j = dateString.lastIndexOf("-");
		if (i != -1 && j != -1) {
			year = Integer.parseInt(dateString.substring(0, i));
		}
		return year;
	}

	/**
	 * 说明：由时间格式的字符串获得月份数
	 * 
	 * 
	 * @param dateString
	 *            时间格式字符串
	 * 
	 * @return
	 */
	public static int getMonthByString(String dateString) {
		int month = 1;
		int i = dateString.indexOf("-");
		int j = dateString.lastIndexOf("-");
		if (i != -1 && j != -1) {
			month = Integer.parseInt(dateString.substring(i + 1, j));
		}
		return month;
	}

	/**
	 * 获取指定日期的天
	 * 
	 * @param dateString
	 * @return
	 */
	public static int getDateByString(String dateString) {
		int date = 1;
		int i = dateString.indexOf("-");
		int j = dateString.lastIndexOf("-");// //System.out.println("i--------------)"+i);
		if (i != -1 && j != -1) {
			date = Integer.parseInt(dateString.substring(j + 1, j + 3));// //System.out.println("date--------------)"+date);
		}
		return date;
	}

	/**
	 * 说明: 字符串转换为日期 (默认格式 yyyy-MM-dd)
	 * 
	 * @param dateString
	 *            日期格式字符串
	 * 
	 * @return 转换后的日期
	 */
	public static Date stringToDate(String dateString) {
		String sf = "yyyy-MM-dd";
		Date dt = stringToDate(dateString, sf);
		return dt;
	}

	/**
	 * 说明：字符串转换为时间（默认格式 yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param dateString
	 *            日期格式字符串
	 * 
	 * @return 转换后的日期
	 */
	public static Date StringToTime(String dateString) {
		String sf = "yyyy-MM-dd HH:mm:ss";
		Date dt = stringToDate(dateString, sf);
		return dt;
	}
	
	/**
	 * 说明：字符串转换为时间（默认格式 yyyy-MM-dd HH:mm:ss)
	 * 根据传入日期将 该日期拼接 23:59:59
	 * 再转化成date 类型
	 *
	 */
	public static Date StringToLastTime(String dateString) {
		String sf = "yyyy-MM-dd HH:mm:ss";
		Date dt = stringToDate(dateString+" 23:59:59", sf);
		return dt;
	}

	/**
	 * 说明：时间字符串格式转换（默认格式 yyyyMMddHHmmss)，主要为接口部分所用
	 * 
	 * 
	 * @param dateString
	 *            日期格式字符串 （默认格式 yyyyMMddHHmmss)
	 * @return 转换后的日期格式字符串（yyyy-MM-dd HH:mm:ss） add by zhangjie
	 */
	public static String timeStringChange(String dateString) {
		String sf = "yyyyMMddHHmmss";
		Date dt = stringToDate(dateString, sf);
		String sc = dateToString(dt);
		return sc;
	}

	/**
	 * 获得当前日期的年月，形成格式为YYMM的字符串
	 * 
	 * @return String 年月字符串，格式YYMM
	 */
	public static String getPublicDate() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		String pd = ("" + c.get(Calendar.YEAR)).substring(2, 4) + (month > 9 ? "" + month : "0" + month);
		return pd;
	}

	/**
	 * 根据参数计算并给出当前日期返回制定月所在的年
	 * 
	 * 
	 * @param getback
	 *            int 返回制定月所在的年
	 * 
	 * @return int
	 */
	public static int backyear(int getback) {
		int back_year = 0;
		CurrTime = new java.util.Date();
		int nowyear = Integer.parseInt(FormatDate(CurrTime, "yyyy"));
		int nowmonth = Integer.parseInt(FormatDate(CurrTime, "MM"));
		if (nowmonth > getback) {
			back_year = nowyear;
		} else {
			back_year = nowyear - 1;
		}
		return back_year;
	}

	/**
	 * 根据参数计算并给出当前日期返回制定月所在的年
	 * 
	 * 
	 * @param getback
	 *            int 返回制定月所在的年
	 * 
	 * @return int
	 */
	public static int backmonth(int getback) {
		int back_month = 0;
		CurrTime = new java.util.Date();
		int nowyear = Integer.parseInt(FormatDate(CurrTime, "yyyy"));
		int nowmonth = Integer.parseInt(FormatDate(CurrTime, "MM"));
		if (nowmonth > getback) {
			back_month = nowmonth - getback;
		} else {
			back_month = nowmonth - getback + 12;
		}
		return back_month;
	}

	/**
	 * 根据参数计算并给出所需的年
	 * 
	 * @param date
	 *            Date 日期
	 * @param getback
	 *            int 返回制定月所在的年
	 * 
	 * @return String
	 */
	public static String dateToyear(Date date, int getback) {
		String dateyear = FormatDate(date, "yyyy");
		String datemonth = FormatDate(date, "MM");
		String newyear = "";
		int oldyear = Integer.parseInt(dateyear);
		int oldmonth = Integer.parseInt(datemonth);
		if (oldmonth > getback) {
			newyear = String.valueOf(oldyear);
		} else {
			newyear = String.valueOf(oldyear - 1);
		}
		return newyear;
	}

	/**
	 * 根据参数计算并给出所需的月
	 * 
	 * @param date
	 *            Date 日期
	 * @param getback
	 *            int 返回制定月所在的年
	 * 
	 * @return String
	 */
	public static String dateTomonth(Date date, int getback) {
		String dateyear = FormatDate(date, "yyyy");
		String datemonth = FormatDate(date, "MM");
		String newmonth = "";
		int oldmonth = Integer.parseInt(datemonth);
		if (oldmonth > getback) {
			newmonth = String.valueOf(oldmonth - getback);
		} else {
			newmonth = String.valueOf(oldmonth + 12 - getback);
		}
		return newmonth;
	}

	/**
	 * 根据给定的两个时间计算时间差
	 * 
	 * @param date_1
	 *            Date 日期
	 * @param date_2
	 *            Date 日期
	 * @return int
	 */
	public static int subtime(Date date_1, Date date_2) {
		String dateyear_1 = FormatDate(date_1, "yyyy");
		String datemonth_1 = FormatDate(date_1, "MM");
		String dateyear_2 = FormatDate(date_2, "yyyy");
		String datemonth_2 = FormatDate(date_2, "MM");
		int year_1 = Integer.parseInt(dateyear_1);
		int year_2 = Integer.parseInt(dateyear_2);
		int month_1 = Integer.parseInt(datemonth_1);
		int month_2 = Integer.parseInt(datemonth_2);
		int subtime = (year_2 - year_1) * 12 + (month_2 - month_1) + 1;
		return subtime;
	}

	/**
	 * 根据给定的两个时间字符串计算时间差
	 * 
	 * 
	 * @param dateString1
	 *            String 时间
	 * @param dateString2
	 *            String 时间
	 * @return long 时间差以秒为单位(dateString2-dateString1)
	 */
	public static long subsecond(String dateString1, String dateString2) {
		Date date_1 = StringToTime(dateString1);
		Date date_2 = StringToTime(dateString2);
		long subsecond = (date_2.getTime() - date_1.getTime()) / 1000;
		return subsecond;
	}

	/**
	 * 将一个表示秒数的字符串转换为n小时n分钟n秒的字符串
	 * 
	 * 
	 * @param dateString
	 *            String 日期字符串
	 * 
	 * @return String
	 */
	public static String changeFormat(String dateString) {
		int date = Integer.parseInt(dateString);
		int hh = 0;
		int mm = 0;
		int ss = 0;
		hh = date / 3600;
		mm = date % 3600 / 60;
		ss = date % 3600 % 60;
		String changedtime = hh + "小时";
		if (mm != 0) {
			changedtime += (hh + "分钟");
		}
		if (ss != 0) {
			if (mm == 0) {
				changedtime += "0分钟";
			}
			changedtime += (ss + "秒");
		}
		return changedtime;
	}

	/**
	 * 将一个表示秒数的字符串转换为天数
	 * 
	 * @param timelength
	 *            long 秒数
	 * @return long
	 */
	public static long changeSecondToDay(long timelength) {
		long day = 0;
		if (timelength % (24 * 3600) == 0) {
			day = timelength / 24 / 3600;
		} else {
			day = timelength / 24 / 3600 + 1;
		}
		return day;
	}

	/**
	 * 将一个表示秒数的字符串转换为分钟
	 * 
	 * @param timelengthString
	 *            String 秒数的字符串
	 * @return String
	 */
	public static String changeSecondToMinute(String timelengthString) {
		long minute = 0;
		String minuteString = "";
		long timelength = Long.parseLong(timelengthString);
		if (timelength % 60 == 0) {
			minute = timelength / 60;
		} else {
			minute = timelength / 60 + 1;
		}
		minuteString = Long.toString(minute) + "分";
		return minuteString;
	}

	/**
	 * 改变日到分
	 * 
	 * 
	 * @param dayString
	 *            String 日字符串
	 * @return String
	 */
	public static String changeDayToMinute(String dayString) {
		long minute = 0;
		long day = Long.parseLong(dayString);
		String minuteString = "";
		minute = day * 24 * 60;
		minuteString = Long.toString(minute) + "分";
		return minuteString;
	}

	/**
	 * 改变分到小时
	 * 
	 * @param minuteString
	 *            String 分字符串
	 * @return String
	 */
	public static String changeMinuteToHour(String minuteString) {
		long minute = Long.parseLong(minuteString);
		long hour = 0;
		String hourString = "";
		if (minute % 60 == 0) {
			hour = minute / 60;
			hourString = Long.toString(hour) + "小时";
		} else {
			hour = minute;
			hourString = Long.toString(hour) + "分钟";
		}
		return hourString;
	}

	/**
	 * 字符串转换为日期
	 * 
	 * @param dateString
	 *            String 日期格式字符串
	 * 
	 * @param sf
	 *            String 日期格式化定义
	 * 
	 * @return Date 转换后的日期
	 */
	public static Date stringToDate(String dateString, String sf) {
		ParsePosition pos = new ParsePosition(0);
		SimpleDateFormat sdf = new SimpleDateFormat(sf);
		Date dt = sdf.parse(dateString, pos);
		return dt;
	}

	/**
	 * 取得月份字符串
	 * 
	 * 
	 * @return String
	 */
	public static String getMonthStr() {
		java.util.Date date = new java.util.Date();
		return (date.getYear() + 1900) + "" + ("" + date.getMonth());
	}

	/**
	 * 将从oracle中取出的Date类型字符串（YYYY－MM－DD HH24：MI：SS.ms）转化为YYYY-MM-DD HH24:MI:SS
	 * 
	 * @param date
	 *            String 日期
	 * @return String
	 */
	public static String changeOracleDate(String date) {
		if (date.indexOf(".") != -1) {
			return date.substring(0, date.indexOf("."));
		} else {
			return date;
		}
	}

	public static String getCurrTimes() {
		java.util.Date date_time = new Date();
		return FormatDate(date_time, "HH:mm:ss");
	}

	/**
	 * 根据传近来的时间和时间格式得到对应的星期
	 * 
	 * @param TempDate
	 *            时间
	 * @param format
	 *            "yyyy-MM-dd"
	 * @return int 1~7代表星期日到星期六
	 * 
	 * @deprecated Use {@link #getWeekDay(String,String)} instead
	 */
	public static int GetWeekDay(String TempDate, String format) {
		return getWeekDay(TempDate, format) + 1;
	}

	/**
	 * 根据传近来的时间和时间格式得到对应的星期
	 * 
	 * @param TempDate
	 *            时间
	 * @param format
	 *            "yyyy-MM-dd"
	 * @return int 0~6代表星期日到星期六
	 */
	public static int getWeekDay(String TempDate, String format) {
		int temp = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(TempDate));
			temp = c.get(Calendar.DAY_OF_WEEK);
		} catch (Exception e) {

		}
		return temp - 1;
	}

	/**
	 * 根据 传入的时间、时间格式 和 天数，返回指定的日期 time 当前时间 format 时间格式 yyyy-MM-dd HH:mm:ss date
	 * 天数，如果是后3天则 -3，前3天则 是 3
	 */
	public static String getTimeToAddDate(String time, String format, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getDefault());
		calendar.clear();
		calendar.setTime(stringToDate(time, format));// 开始时间

		calendar.add(Calendar.DAY_OF_MONTH, date);

		return FormatDate(calendar.getTime(), format);
	}

	/**
	 * 获取某个日期在本年度所在第几周
	 * 
	 * @param dateString
	 * @return
	 */
	public static int getWeeknumOfYear(String dateString) {
		int weeknum = 1;
		String year = dateString.substring(0, 4);
		String newYearsDay = year + "-01-01";
		Date date_1 = DateUtils.stringToDate(newYearsDay);
		Date date_2 = DateUtils.stringToDate(dateString);
		long subsecond = (date_2.getTime() - date_1.getTime()) / 1000;
		int days = (int) (subsecond / (3600 * 24));
		int newYearsWeek = DateUtils.getDayOfWeek(Integer.parseInt(year), 1, 1) - 1;
		weeknum = (newYearsWeek + days) / 7 + 1;
		return weeknum;
	}

	/**
	 * 将阿拉伯数字转换成中文星期几
	 * 
	 * @param i
	 * @return
	 */
	public static String getChineseNum(int i) {
		switch (i) {
		case 0:
			return "日";
		case 1:
			return "一";
		case 2:
			return "二";
		case 3:
			return "三";
		case 4:
			return "四";
		case 5:
			return "五";
		case 6:
			return "六";
		default:
			return null;
		}
	}

	/**
	 * 根据 传入的时间、时间格式 和 天数，返回指定时间段后多少毫秒会自动追加 time 当前时间 format 时间格式 yyyy-MM-dd
	 * HH:mm:ss timeInMillis 毫秒，表示当前时间段后多少毫秒会自动追加
	 */
	public static String getTimeInMillisToAdd(String time, String format, long timeInMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getDefault());
		calendar.clear();
		calendar.setTime(stringToDate(time, format));// 开始时间

		calendar.setTimeInMillis(calendar.getTimeInMillis() + timeInMillis);
		return dateToString(calendar.getTime());
	}

	/**
	 * 根据 传入的时间、时间格式 ，返回毫秒
	 * 
	 * time 指定时间 format 时间格式 yyyy-MM-dd HH:mm:ss
	 */
	public static long getTimeInMillis(String time, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getDefault());
		calendar.clear();
		calendar.setTime(stringToDate(time, format));// 开始时间

		return calendar.getTimeInMillis();
	}

	/**
	 * 传一个时间段得到这个时间段中有多少个周末
	 * 
	 * @param beginDay
	 *            int 2007-01-01
	 * @param endDay
	 *            int 2007-01-31
	 * @return int 1－31的一天
	 */
	public static int getWeekDays(String beginDay, String endDay) {
		int k = 0;
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTimeZone(TimeZone.getDefault());
		calBegin.clear();
		calBegin.setTime(stringToDate(beginDay));// 开始时间

		Calendar calEnd = Calendar.getInstance();
		calEnd.setTimeZone(TimeZone.getDefault());
		calEnd.clear();
		calEnd.setTime(stringToDate(endDay));// 结束时间

		while (calBegin.before(calEnd)) {
			// 星期天 是 1 ，星期6 为 7
			if (calBegin.get(calBegin.DAY_OF_WEEK) == 1 || calBegin.get(calBegin.DAY_OF_WEEK) == 7) {
				++k; // 有就给K加1
			}
			calBegin.roll(calBegin.DATE, true); // 当前日期向前动1天递增
			// //System.out.println(calBegin.getTime());
			if (calBegin.after(calEnd)) { // 开始时间大于结束时间 就结束循环

				break;
			}
		}
		if (getWeekDay(endDay, "yyyy-MM-dd") == 1 || getWeekDay(endDay, "yyyy-MM-dd") == 7) {
			k += 1;
		}
		return k;
	}

	/**
	 * 计算星期几的函数
	 * 
	 * @param year
	 *            int
	 * @param month
	 *            int
	 * @param date
	 *            int
	 * @return int 1~7代表星期日到星期六
	 */
	public static int getDayOfWeek(int year, int month, int date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getDefault());
		cal.clear();
		cal.set(year, month - 1, date);
		return cal.get(cal.DAY_OF_WEEK);
	}

	/**
	 * 计算星期几的函数
	 * 
	 * @param year
	 *            int
	 * @param month
	 *            int
	 * @param date
	 *            int
	 * @return int 1~7代表星期日到星期六
	 */
	public static String getDayOfWeekname(int year, int month, int date) {
		int num = 0;
		String week = null;
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getDefault());
		cal.clear();
		cal.set(year, month - 1, date);
		num = cal.get(cal.DAY_OF_WEEK);
		if (num == 1) {
			week = "星期日";
		} else if (num == 2) {
			week = "星期一";
		} else if (num == 3) {
			week = "星期二";
		} else if (num == 4) {
			week = "星期三";
		} else if (num == 5) {
			week = "星期四";
		} else if (num == 6) {
			week = "星期五";
		} else if (num == 7) {
			week = "星期六";
		}

		return week;
	}

	/**
	 * 根据传进来的参数得到当前月最后一天
	 */
	public static int getLastDayOfMonth(String time, String format) {
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(stringToDate(time, format));
		int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
		return lastDay;
	}

	/**
	 * 获取两个年月之间的月差(maxdate-mindate)
	 * 
	 * @author caibh
	 * @param mindate
	 *            格式：2008-08
	 * @param maxdate
	 *            格式：2008-08
	 * @return 月数
	 */
	public static int getMonthsBetween(String mindate, String maxdate) {
		if (isBlank(mindate) || isBlank(maxdate)) {
			return 0;
		}
		String minyear = mindate.substring(0, 4);
		String minmonth = mindate.substring(5);
		String maxyear = maxdate.substring(0, 4);
		String maxmonth = maxdate.substring(5);
		return (Integer.parseInt(maxyear) - Integer.parseInt(minyear)) * 12 + (Integer.parseInt(maxmonth) - Integer.parseInt(minmonth));
	}

	/**
	 * 判断字符串是否为空或null
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		if (str == null || "".equals(str))
			return true;
		else
			return false;
	}

	/**
	 * 格式化公文日期
	 * 
	 * @param str
	 *            源字符串中的字符2008-11-10
	 * @return
	 */
	public static String formatOfficialDate(String str, String flag) {
		StringBuffer sb = new StringBuffer();
		int pos1 = str.indexOf("-");
		int pos2 = str.lastIndexOf("-");

		if ("upper".equalsIgnoreCase(flag)) {
			for (int i = 0; i < 4; i++) {
				sb.append(upperDigit(str.charAt(i)));
			}
			sb.append('年');
			if (getMidLen(str, pos1, pos2) == 1) {
				sb.append(upperDigit(str.charAt(5)) + "月");

				if (str.charAt(7) != '0') {
					if (getLastLen(str, pos2) == 1) {
						sb.append(upperDigit(str.charAt(7)) + "日");
					}
					if (getLastLen(str, pos2) == 2) {
						if (str.charAt(7) != '1' && str.charAt(8) != '0') {
							sb.append(upperDigit(str.charAt(7)) + "十" + upperDigit(str.charAt(8)) + "日");
						} else if (str.charAt(7) != '1' && str.charAt(8) == '0') {
							sb.append(upperDigit(str.charAt(7)) + "十日");
						} else if (str.charAt(7) == '1' && str.charAt(8) != '0') {
							sb.append("十" + upperDigit(str.charAt(8)) + "日");
						} else {
							sb.append("十日");
						}
					}
				} else {
					sb.append(upperDigit(str.charAt(8)) + "日");
				}
			}
			if (getMidLen(str, pos1, pos2) == 2) {
				if (str.charAt(5) != '0' && str.charAt(6) != '0') {
					sb.append("十" + upperDigit(str.charAt(6)) + "月");

					if (getLastLen(str, pos2) == 1) {
						sb.append(upperDigit(str.charAt(8)) + "日");
					}
					if (getLastLen(str, pos2) == 2) {
						if (str.charAt(8) != '0') {
							if (str.charAt(8) != '1' && str.charAt(9) != '0') {
								sb.append(upperDigit(str.charAt(8)) + "十" + upperDigit(str.charAt(9)) + "日");
							} else if (str.charAt(8) != '1' && str.charAt(9) == '0') {
								sb.append(upperDigit(str.charAt(8)) + "十日");
							} else if (str.charAt(8) == '1' && str.charAt(9) != '0') {
								sb.append("十" + upperDigit(str.charAt(9)) + "日");
							} else {
								sb.append("十日");
							}
						} else {
							sb.append(upperDigit(str.charAt(9)) + "日");
						}
					}
				} else if (str.charAt(5) != '0' && str.charAt(6) == '0') {
					sb.append("十月");
					if (getLastLen(str, pos2) == 1) {
						sb.append(upperDigit(str.charAt(8)) + "日");
					}
					if (getLastLen(str, pos2) == 2) {
						if (str.charAt(8) != '0') {
							if (str.charAt(8) != '1' && str.charAt(9) != '0') {
								sb.append(upperDigit(str.charAt(8)) + "十" + upperDigit(str.charAt(9)) + "日");
							} else if (str.charAt(8) != '1' && str.charAt(9) == '0') {
								sb.append(upperDigit(str.charAt(8)) + "十日");
							} else if (str.charAt(8) == '1' && str.charAt(9) != '0') {
								sb.append("十" + upperDigit(str.charAt(9)) + "日");
							} else {
								sb.append("十日");
							}
						} else {
							sb.append(upperDigit(str.charAt(9)) + "日");
						}
					}
				} else {
					sb.append(upperDigit(str.charAt(6)) + "月");

					if (getLastLen(str, pos2) == 1) {
						sb.append(upperDigit(str.charAt(8)) + "日");
					}
					if (getLastLen(str, pos2) == 2) {
						if (str.charAt(8) != '0') {
							if (str.charAt(8) != '1' && str.charAt(9) != '0') {
								sb.append(upperDigit(str.charAt(8)) + "十" + upperDigit(str.charAt(9)) + "日");
							} else if (str.charAt(8) != '1' && str.charAt(9) == '0') {
								sb.append(upperDigit(str.charAt(8)) + "十日");
							} else if (str.charAt(8) == '1' && str.charAt(9) != '0') {
								sb.append("十" + upperDigit(str.charAt(9)) + "日");
							} else {
								sb.append("十日");
							}
						} else {
							sb.append(upperDigit(str.charAt(9)) + "日");
						}
					}
				}
			}
		} else {
			String y = str.substring(0, pos1);
			String m = str.substring(pos1 + 1, pos2);
			String d = str.substring(pos2 + 1);
			sb.append(y + "年");
			sb.append(m + "月");
			sb.append(d + "日");
			// //System.out.println("y==="+y+"m==="+m+"d===="+d);
		}

		return sb.toString();
	}

	/**
	 * 将源字符串中的阿拉伯数字格式化为汉字
	 * 
	 * @param sign
	 *            源字符串中的字符
	 * @return
	 */
	public static char upperDigit(char sign) {
		if (sign == '0')
			sign = '○';
		if (sign == '1')
			sign = '一';
		if (sign == '2')
			sign = '二';
		if (sign == '3')
			sign = '三';
		if (sign == '4')
			sign = '四';
		if (sign == '5')
			sign = '五';
		if (sign == '6')
			sign = '六';
		if (sign == '7')
			sign = '七';
		if (sign == '8')
			sign = '八';
		if (sign == '9')
			sign = '九';

		return sign;
	}

	/**
	 * 获得月份字符串的长度
	 * 
	 * @param str
	 *            待转换的源字符串
	 * @param pos1
	 *            第一个'-'的位置
	 * 
	 * @param pos2
	 *            第二个'-'的位置
	 * 
	 * @return
	 */
	public static int getMidLen(String str, int pos1, int pos2) {
		return str.substring(pos1 + 1, pos2).length();
	}

	/**
	 * 获得日期字符串的长度
	 * 
	 * @param str
	 *            待转换的源字符串
	 * @param pos2
	 *            第二个'-'的位置
	 * 
	 * @return
	 */
	public static int getLastLen(String str, int pos2) {
		return str.substring(pos2 + 1).length();
	}

  public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }
	 
    // 获取当前时间所在年的最大周数
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
 
        return getWeekOfYear(c.getTime());
    }
    
	public static String getWeekOfDate(Date date) {
		String[] weekDaysName = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		//String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysName[intWeek];
	}
    //获取当前日期的后一天
	public static Date getNextDay(Date date) {
	    Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
    }
	
	/**
	 * @描述 当天的23：59：59
	 * @作者 wum
	 * @时间 2017年4月16日下午1:56:45
	 * @参数 @param date
	 * @参数 @return
	 * @return Date
	 */
	public static Date getDayEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * @描述 验证是否为按指定符号分割的日期
	 * @作者 chenxiaobin
	 * @时间 2017年4月16日下午1:56:45
	 * @参数 @param strDate 字符串的日期   regex分隔符
	 * @参数 @return
	 * @return Date
	 */
	public static boolean isDate(String strDate,String regex) {
		boolean flag = true;
		try {
			String[] arr = strDate.split(regex);
			int year = 0,month = 0,day = 0;
			
			for(int i=0;i<arr.length;i++) {
				
				switch(i) {
					case 0:
						year = Integer.parseInt(arr[0]);
						if(year<=0 || year>=10000) {
							return false;
						}
						break;
					case 1:
						month = Integer.parseInt(arr[1]);
						if(month<=0 || month>=13) {
							return false;
						}
						break;
					case 2:
						day = Integer.parseInt(arr[2]);
						if(run(year)) {
							if (arr[1].matches("0[2]||2")) {// 这里是闰年的2月
				                 if (!arr[2].matches("0[1-9]||[1-9]||1[0-9]||2[0-9]")) {
				                    return false; 
				                 }
							}
						}else {
							if (arr[1].matches("0[2]||2")) {// 这里是平年的2月
				                if (!arr[2].matches("0[1-9]||[1-9]||1[0-9]||2[0-8]")) {
				                    return false;
				                }
				            }
						}
						// 下面判断除了2月份的大小月天数
				        if (arr[1].matches("0[13578]||[13578]||1[02]")) {// 这里是大月
				            if (!arr[2].matches("0[1-9]||[1-9]||[12][0-9]||3[01]")) {
				                return false;
				            }
				        } else if (arr[1].matches("0[469]||[469]||11")) {// 这里是小月
				            if (!arr[2].matches("0[1-9]||[1-9]||[12][0-9]||30")) {
				                return false;
				            }
				        }
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		
		return flag;
    }
	
	
	/**
	      * 检查是否是闰年
	      * 
	      * @param year
	      * @return
	      */
 	public static boolean run(int year) {
         if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {// 是闰年
             return true;
         } else {
             return false;
         }
     }
}
