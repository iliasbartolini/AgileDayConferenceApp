package it.agileday.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dates {
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Date newDate(int year, int month, int day) {
		return newDate(year, month, day, 0);
	}

	public static Date newDate(int year, int month, int day, int hour) {
		return newDate(year, month, day, hour, 0);
	}

	public static Date newDate(int year, int month, int day, int hour, int minute) {
		return newDate(year, month, day, hour, minute, 0);
	}

	public static Date newDate(int year, int month, int day, int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month - 1, day, hour, minute, second);
		return calendar.getTime();
	}

	public static boolean before(Date d1, Date d2) {
		return compare(d1, d2) < 0;
	}

	public static boolean beforeOrEqual(Date d1, Date d2) {
		return compare(d1, d2) <= 0;
	}

	public static boolean after(Date d1, Date d2) {
		return compare(d1, d2) > 0;
	}

	public static boolean afterOrEqual(Date d1, Date d2) {
		return compare(d1, d2) >= 0;
	}

	public static boolean equal(Date d1, Date d2) {
		return compare(d1, d2) == 0;
	}

	public static int compare(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		return c1.compareTo(c2);
	}

	public static Date fromString(String date) {
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(String.format("Cannot parse '%s' as date", date), e);
		}
	}

	public static String toString(Date date) {
		return dateFormat.format(date);
	}
}
