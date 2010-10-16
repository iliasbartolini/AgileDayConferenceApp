package it.agileday.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Dates {
	private static final DateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
	private static final DateFormat tweeterDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

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

	public static Date fromDbString(String date) {
		try {
			return sqlDateFormat.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(String.format("Cannot parse '%s' as date", date), e);
		}
	}

	public static String toDbString(Date date) {
		return sqlDateFormat.format(date);
	}

	public static String toString(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	public static Date fromTweeterString(String date) {
		try {
			return tweeterDateFormat.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(String.format("Cannot parse '%s' as tweeterdate", date), e);
		}
	}

	public static String differenceSmart(Date d1, Date d2) {
		int dayDiff = differenceDays(d1, d2);
		int hourDiff = differenceHours(d1, d2);
		int minDiff = differenceMinutes(d1, d2);

		String smartDescription = "";
		int whichIsSmartValue = 0;

		if (dayDiff != 0) {
			smartDescription = "day";
			whichIsSmartValue = dayDiff;
		} else if (hourDiff != 0) {
			smartDescription = "hour";
			whichIsSmartValue = hourDiff;
		} else if (minDiff != 0) {
			smartDescription = "minute";
			whichIsSmartValue = minDiff;
		} else {
			smartDescription = "now";
			whichIsSmartValue = 0;
		}

		if (Math.abs(whichIsSmartValue) > 1) {
			smartDescription = Math.abs(whichIsSmartValue) + " " + smartDescription + "s";
		} else if (Math.abs(whichIsSmartValue) == 1) {
			smartDescription = "one " + smartDescription;
		}

		if (whichIsSmartValue > 0) {
			smartDescription = smartDescription + " ago";
		} else if (whichIsSmartValue < 0) {
			smartDescription = smartDescription + " from now";
		}

		return smartDescription;
	}

	public static int differenceDays(Date d1, Date d2) {
		return differenceSeconds(d1, d2) / 86400;
	}

	public static int differenceHours(Date d1, Date d2) {
		return differenceSeconds(d1, d2) / 3600;
	}

	public static int differenceMinutes(Date d1, Date d2) {
		return differenceSeconds(d1, d2) / 60;
	}

	public static int differenceSeconds(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		return (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / 1000);
	}
}
