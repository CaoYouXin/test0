/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CPU
 */
public final class DateUtils {

	private static final DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final DateFormat FORMATTER2 = new SimpleDateFormat("yyyy-MM-dd");

	public static enum TIMEUNIT {

		second,
		minute,
		hour,
		day;
	}

	private static final long SECOND = 1000;
	private static final long MINUTE = 60 * SECOND;
	private static final long HOUR = 60 * MINUTE;
	private static final long DAY = 24 * HOUR;

	/**
	 *
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String formatDateTime(Date date) {
		return FORMATTER.format(date);
	}

	/**
	 *
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date parseDateTime(String date) {
		try {
			return FORMATTER.parse(date);
		} catch (ParseException ex) {
			Logger.getLogger(DateUtils.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	/**
	 *
	 * @param date
	 * @return yyyy-MM-dd
	 */
	public static String formatDate(Date date) {
		return FORMATTER2.format(date);
	}

	/**
	 *
	 * @param date yyyy-MM-dd
	 * @return
	 */
	public static Date parseDate(String date) {
		try {
			return FORMATTER2.parse(date);
		} catch (ParseException ex) {
			Logger.getLogger(DateUtils.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static boolean before(Date d1, Date d2) {
		return d2.getTime() > d1.getTime();
	}

	public static boolean beforeOrEqual(Date d1, Date d2) {
		return d2.getTime() >= d1.getTime();
	}

	public static long before(Date date, Date now, TIMEUNIT unit) {
		return howLong(now.getTime() - date.getTime(), unit);
	}

	public static long later(Date date, Date now, TIMEUNIT unit) {
		return howLong(date.getTime() - now.getTime(), unit);
	}

	public static Date before(int what, TIMEUNIT unit, Date now) {
		return new Date(now.getTime() - howLong(what, unit));
	}

	public static Date later(int what, TIMEUNIT unit, Date now) {
		return new Date(now.getTime() + howLong(what, unit));
	}

	public static long last(Date from, Date to, TIMEUNIT unit) {
		return howLong(to.getTime() - from.getTime(), unit);
	}
	
	private static long howLong(int what, TIMEUNIT unit) throws RuntimeException {
		long howLong = 0;
		switch (unit) {
			case second:
				howLong = SECOND * what;
				break;
			case minute:
				howLong = MINUTE * what;
				break;
			case hour:
				howLong = HOUR * what;
				break;
			case day:
				howLong = DAY * what;
				break;
			default:
				throw new RuntimeException("算时间都出错！");
		}
		return howLong;
	}

	private static long howLong(long howLong, TIMEUNIT unit) throws RuntimeException {
		long what = 0;
		switch (unit) {
			case second:
				what = howLong / SECOND;
				break;
			case minute:
				what = howLong / MINUTE;
				break;
			case hour:
				what = howLong / HOUR;
				break;
			case day:
				what = howLong / DAY;
				break;
			default:
				throw new RuntimeException("算时间都出错！");
		}
		return what;
	}

}
