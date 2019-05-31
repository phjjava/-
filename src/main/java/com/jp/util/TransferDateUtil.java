package com.jp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期的转化类，用于满足手机端时间的显示格式
 * @author Administrator
 *
 */
public class TransferDateUtil {
	
	/**
	 * 格式化时间
	 * @param time
	 * @return
	 */
	public static String formatDateTime(Date date) {
		
		SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		String time = format.format(date);
		
		if(time==null ||"".equals(time)){
			return "";
		}
		try {
			date = format.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Calendar current = Calendar.getInstance();
		
		Calendar today = Calendar.getInstance();	//今天
		
		today.set(Calendar.YEAR, current.get(Calendar.YEAR));
		today.set(Calendar.MONTH, current.get(Calendar.MONTH));
		today.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
		//  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
		today.set( Calendar.HOUR_OF_DAY, 0);
		today.set( Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		
		Calendar yesterday = Calendar.getInstance();	//昨天
		
		yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
		yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
		yesterday.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH)-1);
		yesterday.set( Calendar.HOUR_OF_DAY, 0);
		yesterday.set( Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);
		
		current.setTime(date);
		
		if(current.after(today)){
			return "今天 "+time.split(" ")[1];
		}else if(current.before(today) && current.after(yesterday)){
			return "昨天 "+time.split(" ")[1];
		}else{
			int index = time.indexOf("-")+1;
			return time.substring(index, time.length());
		}
	}
	
    /**
     * 获取yyyy-MM-dd HH:mm:ss到yyyy-MM-01 00:00:00的毫秒数
     * @return
     * @throws ParseException 
     */
    public static long getCurYearSeconds() throws ParseException{
    	
    	DateFormat format = new SimpleDateFormat("yyyy");
		
		Date start = new Date();
		
		String endstr = format.format(start) + "-01-01 00:00:00";
		
		Date end = format.parse(endstr);
		
		//毫秒数
		long seccounts = start.getTime()-end.getTime();
		
    	return seccounts/1000;
    	
    }
    
    /**
     * 获取yyyy-MM-dd HH:mm:ss到yyyy-MM-dd 00:00:00的毫秒数
     * @return
     * @throws ParseException
     */
    public static long getCurDaySeconds() throws ParseException{
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Date start = new Date();
		
		String endstr = format.format(start) + " 00:00:00";
		
		Date end = format.parse(endstr);
		
		//毫秒数
		long seccounts = start.getTime()-end.getTime();
		
    	return seccounts/1000;
    }
	
	public static String transferDate(Date end) {
		
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			Date start = new Date();
			
			long curTodaySeconds = getCurDaySeconds();
			
			long curYearSeconds = getCurYearSeconds();

			//毫秒数
			long seccounts = start.getTime()-end.getTime();
			
			seccounts = seccounts/1000;
			
			String sb = "";
			
			if(seccounts <= 60){//小于1分钟
				sb = seccounts + "秒前";
			}else if(seccounts > 60 && seccounts <= 3600){//小于1小时
				sb = seccounts/60 + "分钟前";
			}else if(seccounts > 3600 && seccounts <= curTodaySeconds){//小于当天24点
				format = new SimpleDateFormat("HH:mm");
				sb = "今天" + format.format(end);
			}else if(seccounts > curTodaySeconds && seccounts <= curYearSeconds){//小于今年
				format = new SimpleDateFormat("MM月dd日 HH:mm");
				sb = format.format(end);
			}else{//大于今年
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				sb = format.format(end);
			}
			
			return sb;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws ParseException {
	
		String str = "2015-01-15 12:44:47";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date end = format.parse(str);
		System.out.println(formatDateTime(end));

	}
}
