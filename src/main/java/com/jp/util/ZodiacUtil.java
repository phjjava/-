package com.jp.util;


 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
 
/**
 * 根据日期，转换成星座、生肖工具类
 *
 */
public class ZodiacUtil {
 
	public static final String[] zodiacArray = { "申猴", "酉鸡", "戌狗", "亥猪", "子鼠", "丑牛",
			"寅虎", "卯兔", "辰龙", "巳蛇", "午马", "未羊" };
 
	public static final String[] constellationArray = { "水瓶座", "双鱼座", "牡羊座",
			"金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座" };
 
	public static final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22,
			23, 23, 23, 23, 22, 22 };
 
	/**
	 * 根据日期获取生肖
	 * 
	 * @return
	 */
	public static String date2Zodica(Calendar time) {
		return zodiacArray[time.get(Calendar.YEAR) % 12];
	}
	/**
	 * 根据日期获取生肖
	 * 
	 * @return
	 */
	public static String date2Zodica(int year) {
		return zodiacArray[year % 12];
	}
	/**
	 * 根据日期获取生肖
	 * 
	 * @return
	 */
	public static String date2Zodica(String time) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = sdf.parse(time);
			c.setTime(date);
 
			String zodica = date2Zodica(c);
			//System.out.println("生肖：" + zodica);
			return zodica;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
 
	/**
	 * 根据日期获取星座
	 * 
	 * @param time
	 * @return
	 */
	public static String date2Constellation(Calendar time) {
		int month = time.get(Calendar.MONTH);
		int day = time.get(Calendar.DAY_OF_MONTH);
		if (day < constellationEdgeDay[month]) {
			month = month - 1;
		}
		if (month >= 0) {
			return constellationArray[month];
		}
		// default to return 魔羯
		return constellationArray[11];
	}
	/**
	 * 根据日期获取星座
	 * 
	 * @param time
	 * @return
	 */
	public static String date2Constellation(String time) {
 
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = sdf.parse(time);
			c.setTime(date);
 
			String constellation = date2Constellation(c);
			//System.out.println("星座：" + constellation);
			return constellation;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	
	}


}