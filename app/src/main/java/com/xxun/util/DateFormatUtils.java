package com.xxun.util;

import com.xxun.xunintegral.Canstance;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormatUtils {

 	 /**
   	 	* 获取现在时间
    	*
   		 * @return 返回短时间字符串格式yyyy-MM-dd
    	*/
	 public static String getNowDateShort() {
		   SimpleDateFormat formatter = new SimpleDateFormat(Canstance.FORMATTIME);
		 Date currentTime = new Date(System.currentTimeMillis());
		   String dateString = formatter.format(currentTime);
		   return dateString;
	 }

	public static String getNowDateToStr(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static boolean isToday(String timestamp) {
		try {
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat(Canstance.FORMATTIME);
			Date currentTime = new Date();
			String currentString = formatter1.format(currentTime);
			if (currentString.equals(formatter1.format(formatter2.parse(timestamp)))) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException ex) {
			return false;
		}
	}

	//yyyy-MM-dd HH:mm:ss 格式时间转换成毫秒值
	public static long getMinllisByDate(String time) {
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = null;
		try {
			now = df.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long s = now.getTime() - currentTime;
		return s;
	}


	/**
	 * @param time
	 * 
	 * @return 时:分:秒
	 * 
	 * @author wjh
	 */
	public static String getHours(long time) {
		long second = time / 1000;
		long hour = second / 60 / 60;
		long minute = (second - hour * 60 * 60) / 60;
		long sec = (second - hour * 60 * 60) - minute * 60;

		String rHour = "";
		String rMin = "";
		String rSs = "";
		// 时
		if (hour < 10) {
			rHour = "0" + hour;
		} else {
			rHour = hour + "";
		}
		// 分
		if (minute < 10) {
			rMin = "0" + minute;
		} else {
			rMin = minute + "";
		}
		// 秒
		if (sec < 10) {
			rSs = "0" + sec;
		} else {
			rSs = sec + "";
		}

		// return hour + "小时" + minute + "分钟" + sec + "秒";
		return rHour + ":" + rMin + ":" + rSs;

	}

	/**
	 * @param time
	 *            时间戳
	 * @return 小时
	 */
	public static String getOnlyHours(long time) {
		long second = time / 1000;
		long hour = second / 60 / 60;
		String rHour = "";
		// 时
		if (time > 0 && hour == 0) {
			rHour = (hour + 1) + "";
		} else {
			rHour = hour + "";
		}

		// return hour + "小时" + minute + "分钟" + sec + "秒";
		return rHour + "小时";

	}

	/**
	 * 返回指定时间字符串。
	 * <p>
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String 指定格式的日期字符串.
	 */
	public static String getDateTime(long microsecond) {
		return getFormatDateTime(new Date(microsecond), "yyyy-MM-dd");
	}


	/**
	 * 返回指定时间字符串。
	 * <p>
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String 指定格式的日期字符串.
	 */
	public static String getDateTime(long microsecond, String format) {
		return getFormatDateTime(new Date(microsecond), format);
	}
	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 *
	 * @param dateDate
	 * @return
	 */
	public static String dateToStr(Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 *
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat sdf=new SimpleDateFormat(Canstance.FORMATTIME);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = sdf.parse(strDate, pos);
		return strtodate;
	}


	/**
	 * 根据给定的格式与时间(Date类型的)，返回时间字符串<br>
	 * 
	 * @param date
	 *            指定的日期
	 * @param format
	 *            日期格式字符串
	 * @return String 指定格式的日期字符串.
	 */
	public static String getFormatDateTime(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}



}
