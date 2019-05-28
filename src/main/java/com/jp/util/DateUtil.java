package com.jp.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class DateUtil
{
	/**
	 * 常用时间格式化字符串
	 */
	public static final String DATA_STRING = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * Android传入的时间类型字符串转化Date对象
	 * @param commontimeString
	 * @return
	 */
	public static Date commonTimeStringToDate(String commontimeString) {
		DateFormat format = new SimpleDateFormat(DATA_STRING);
		Date date = null;
		try {
			date = format.parse(commontimeString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将字符串时间转化为yyyy-MM-dd格式的日期对象
	 * @param strDate 字符串时间
	 * @return Date 日期对象 yyyy-MM-dd 00:00:00
	 */
	public static Date string2Date(String strDate) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 将当前时间以传入的格式转化成字符串
	 * @param formate 指定的转化格式
	 * @return String 字符串时间
	 */
	public static String date2str(String formate) {
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat(formate);
		return sf.format(date);
	}
	
	/**
	 * 将传入的long类型的毫秒时间用常用格式(yyyy-MM-dd HH:mm:ss)转化成字符串时间
	 * @param times 毫秒时间
	 * @return String 字符串时间 yyyy-MM-dd HH:mm:ss
	 */
	public static String date2str(long times) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(times * 1000L);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(gc.getTime());
	}
	
	/**
	 * 将传入的long类型的毫秒时间用日期格式(yyyy-MM-dd，短时间格式)转化成字符串时间
	 * @param times 毫秒时间
	 * @return String 字符串时间 yyyy-MM-dd
	 */
	public static String date2shortStr(long times) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(times * 1000L);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(gc.getTime());
	}
	
	/**
	 * 将传入的Date时间对象用常用格式(yyyy-MM-dd HH:mm:ss)转化成字符串时间
	 * @param date 时间对象
	 * @return String 参数为空返回空串，参数不为空返回字符串时间 yyyy-MM-dd HH:mm:ss
	 */
	public static String startDate2str(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(date);
	}
	
	/**
	 * 获得某个日期的结束时间
	 * 将传入的Date时间对象用日期格式(yyyy-MM-dd，短时间格式)转化成字符串时间，再加上 23:59:59
	 * @param endDate 时间对象
	 * @return String 字符串时间 yyyy-MM-dd 23:59:59
	 */
	public static String endDate2str(Date endDate){
		String str = date2shortStr(endDate);
		str += " 23:59:59";
		return str;
	}
	
	/**
	 * 将传入的Date时间对象用日期格式(yyyy-MM-dd，短时间格式)转化成字符串时间
	 * @param date 时间对象
	 * @return String 字符串时间 yyyy-MM-dd
	 */
	public static String date2shortStr(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(date);
	}
	
	/**
	 * 将转入的Date时间对象以指定的格式转化成字符串
	 * @param date 时间对象
	 * @param format 指定的格式化字符串
	 * @return  String 字符串时间
	 */
	public static String date2str(Date date, String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(date);
	}
	  
	/**
	 * 将传入的Date时间对象用常用格式(yyyy-MM-dd HH:mm:ss)转化成字符串时间
	 * @param date 时间对象
	 * @return String 字符串时间 yyyy-MM-dd HH:mm:ss
	 */
	public static String date2str(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat(DATA_STRING);
		return sf.format(date);
	}
	
	/**
	 * 短格式字符串时间转化为Date日期对象
	 * @param dateStr 短格式字符串时间
	 * @return Date 日起对象  yyyy-MM-dd 00:00:00
	 */
	public static Date shortStrToDate(String dateStr) {
		SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dd.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String DateToJuLian(Date date) {
		DecimalFormat df = new DecimalFormat("000");
		Calendar calenda = Calendar.getInstance();
		calenda.setTime(date);
		String SDpmdt1 = String.valueOf(calenda.get(1) - 1900);
		String SDpmdt2 = df.format(calenda.get(6));
		String SDdrqj3 = SDpmdt1 + SDpmdt2;
		
		return SDdrqj3;
	}
	
	public static String JuLianTotime(String time) {
		int ss = 0;
		int mm = 0;
		int hh = 0;
		String s = null;
		String m = null;
		String h = null;
		String TimeString = "";
	
		if ((time == null) || (time.equals("null")) || (time.trim().length() == 0)) {
			return "";
		}
		int Jtime = Integer.parseInt(time);
		ss = Jtime % 100;
		if ((ss < 10) && (ss >= 0)) {
			s = String.valueOf(ss);
			s = "0" + s;
		} else {
			s = String.valueOf(ss);
		}
		
		mm = Jtime / 100 % 100;
		if ((mm < 10) && (mm >= 0)) {
			m = String.valueOf(mm);
			m = "0" + m;
		} else {
			m = String.valueOf(mm);
		}
		
		hh = Jtime / 10000;
		if ((hh < 10) && (hh >= 0)) {
			h = String.valueOf(hh);
			h = "0" + h;
		} else {
			h = String.valueOf(hh);
		}
		TimeString = h + ":" + m + ":" + s;
		return TimeString;
	}
	  
	/**
	 * 计算两个时间的时间差，返回分钟的差值
	 * @param closenowtime 接近现在的时间，也可以是大时间
	 * @param oldtime 老的时间，过去的时间，也就是小时间
	 * @return long 分钟的差值
	 */
	public static long timeDifferenceOfMin(Date closenowtime, Date oldtime) {
		long timediff = closenowtime.getTime() - oldtime.getTime();
		long min = timediff/(60*1000);
		  
		return min;
	}
	
	/**
	 * 获得某个时间所在的那一天的开始时间，即时分秒为 00:00:00
	 * 例如：传入的参数表示 2014-9-5 13:24:19，调用本方法得到2014-9-5 00:00:00
	 * @param anytime 任意日期对象
	 * @return
	 */
	public static Date getOneDayStratTime(Date anytime) {
		String str = date2shortStr(anytime);
		str += " 00:00:00";
		DateFormat format = new SimpleDateFormat(DATA_STRING);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 获得某个时间所在的那一天的开始时间，即时分秒为 23:59:59
	 * 例如：传入的参数表示 2014-9-5 13:24:19，调用本方法得到2014-9-5 23:59:59
	 * @param anytime 任意日期对象
	 * @return
	 */
	public static Date getOneDayEndTime(Date anytime) {
		String str = date2shortStr(anytime);
		str += " 23:59:59";
		DateFormat format = new SimpleDateFormat(DATA_STRING);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 获得一个日期是星期几
	 * @param date 日期对象
	 * @return int 星期一是1...星期天是7
	 */
	public static int dayOfWeek(Date date) {
		int dayOfWeek = 0;
		Calendar cdate = Calendar.getInstance();
		cdate.setTime(date);
		if(cdate.get(Calendar.DAY_OF_WEEK) == 1) {
			dayOfWeek = 7;
		} else {
			dayOfWeek = cdate.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayOfWeek;
	}
	
	/**
	 * 获得某个时间所在的那一周的开始时间，以周一 00:00:00为开始
	 * @param anytime 任意日期对象
	 * @return
	 */
	public static Date getOneWeekStratTime(Date anytime) {
		int dayOfWeek = dayOfWeek(anytime);
		long milliseconds = anytime.getTime();
		long millisecondsDayOfWeekStart = milliseconds - ((dayOfWeek - 1) * 1000 * 60 * 60 * 24);
		Date dayOfWeekStart = new Date(millisecondsDayOfWeekStart);
		return getOneDayStratTime(dayOfWeekStart);
	}
	
	/**
	 * 获得某个时间所在的那一周的结束时间，以周日23:59:59为结束
	 * @param anytime 任意日期对象
	 * @return
	 */
	public static Date getOneWeekEndTime(Date anytime) {
		int dayOfWeek = dayOfWeek(anytime);
		long milliseconds = anytime.getTime();
		long millisecondsDayOfWeekEnd = milliseconds + ((7 - dayOfWeek) * 1000 * 60 * 60 * 24);
		Date dayOfWeekEnd = new Date(millisecondsDayOfWeekEnd);
		return getOneDayEndTime(dayOfWeekEnd);
	}
	
	/**
	 * 获得某个时间所在的那一月的开始时间，以该月1号 00:00:00为开始
	 * @param anytime 任意日期对象
	 * @return
	 */
	public static Date getOneMonthStratTime(Date anytime) {
		Calendar cdate = Calendar.getInstance();
		cdate.setTime(anytime);
		cdate.set(Calendar.DATE, 1);
		long millisecondsFirstDayOfMonth = cdate.getTimeInMillis();
		Date firstDayOfMonth = new Date(millisecondsFirstDayOfMonth);
		return getOneDayStratTime(firstDayOfMonth);
	}
	
	/**
	 * 获得某个时间所在的那一月的结束时间，以该月最后一天23:59:59为结束
	 * @param anytime 任意日期对象
	 * @return
	 */
	public static Date getOneMonthEndTime(Date anytime) {
		Calendar cdate = Calendar.getInstance();
		cdate.setTime(anytime);
		cdate.set(Calendar.DATE, 1);
		cdate.add(Calendar.MONTH, 1);
		cdate.add(Calendar.DATE, -1);
		long millisecondsLastDayOfMonth = cdate.getTimeInMillis();
		Date lastDayOfMonth = new Date(millisecondsLastDayOfMonth);
		return getOneDayEndTime(lastDayOfMonth);
	}
	
	/**
     * 获取当前日期是星期几<br>
     * 
     * @param dt
     * @return 当前日期是星期几
     */
	public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
	
	/**
	 * 获得一个日期对象描述当天的字符串时间形式的开始时间
	 * 例如：传入的参数表示 2014-9-5 13:24:19，调用本方法得到字符串"2014-9-5 00:00:00"
	 * @param date 日期对象
	 * @return
	 */
	public static String getStringStartTimeOfDay(Date date) {
		String str = date2shortStr(date);
		str += " 00:00:00";
		return str;
	}
	
	/**
	 * 获得一个日期对象描述当天的字符串时间形式的结束时间
	 * 例如：传入的参数表示 2014-9-5 13:24:19，调用本方法得到字符串"2014-9-5 23:59:59"
	 * @param date 日期对象
	 * @return
	 */
	public static String getStringEndTimeOfDay(Date date) {
		String str = date2shortStr(date);
		str += " 23:59:59";
		return str;
	}
	
	public static Date getNextDay(){
		//获取当前日期  
		Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        System.out.println(dateString);
		return date;
	}
	
	/**
	 * 在传入的时间对象的分钟级别上进行计算
	 * @param date 计算的基本时间
	 * @param minutes 在基本时间基础上添加的分钟数
	 * @return
	 */
	public static Date calculateTimeForMinute(Date date, Integer minutes) {
		if(minutes == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes.intValue());
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * 判断两个时间（Long型）之间的差距，是否小于规定时间。小于等于：返回true 
	 * @author:qiuyuhua
	 * @time:2015年12月10日 下午4:25:38
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public static boolean isBetweenTimeLessThanSetTime(long starttime, long endtime ,long settime){
		boolean flag = false;
		long betweentime = starttime - endtime;
		betweentime = Math.abs(betweentime);
		if(betweentime<=settime){
			flag = true;
		}
		return flag;
	}
	
	public static void main(String[] args) throws ParseException {
		getNextDay();
	}
}
