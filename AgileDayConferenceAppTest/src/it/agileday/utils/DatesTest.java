package it.agileday.utils;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class DatesTest extends TestCase {
	public void test_date_compare() {
		Date d1 = Dates.newDate(2010, 1, 1, 10, 0, 0);
		Date d2 = Dates.newDate(2010, 1, 1, 10, 0, 1);

		assertTrue(Dates.before(d1, d2));
		assertTrue(Dates.beforeOrEqual(d1, d2));

		assertTrue(Dates.after(d2, d1));
		assertTrue(Dates.afterOrEqual(d2, d1));

		assertTrue(Dates.beforeOrEqual(d1, d1));
		assertTrue(Dates.afterOrEqual(d1, d1));

		assertTrue(Dates.equal(d1, d1));
	}
	
	public void test_string_conversion() {
		Date d1 = Dates.newDate(2010, 12, 31, 10, 0, 0);
		assertEquals("2010-12-31 10:00:00", Dates.toDbString(d1));
		
		assertTrue(Dates.equal(d1, Dates.fromDbString("2010-12-31 10:00:00")));
		
		try {
			Dates.fromDbString("today");
			fail();
		} catch (Exception e) {
			assertEquals("Cannot parse 'today' as date", e.getMessage());
		}
	}
	
	public void test_difference_minutes() {
		Date d1 = Dates.newDate(2010, 1, 1, 10, 30, 0);
		Date d2 = Dates.newDate(2010, 1, 1, 10, 0, 0);
		assertEquals(30, Dates.differenceMinutes(d1, d2));
	}

	public void test_difference_hours() {
		Date d1 = Dates.newDate(2010, 1, 1, 12, 30, 0);
		Date d2 = Dates.newDate(2010, 1, 1, 10, 0, 0);
		assertEquals(2, Dates.differenceHours(d1, d2));

		Date d3 = Dates.newDate(2010, 1, 1, 12, 30, 0);
		assertEquals(0, Dates.differenceHours(d1, d3));
	}
	
	public void test_difference_days() {
		Date d1 = Dates.newDate(2010, 1, 1, 12, 30, 0);
		Date d2 = Dates.newDate(2010, 1, 1, 10, 0, 0);
		assertEquals(0, Dates.differenceDays(d1, d2));

		Date d3 = Dates.newDate(2010, 1, 10, 10, 0, 0);
		assertEquals(8, Dates.differenceDays(d3, d1));
	}

	public void test_parse_twitter_dates(){
		String sampleDate = "Sun, 10 Oct 2010 20:56:58 +0002";
		try {
			Date tweetDate = Dates.fromTweeterString(sampleDate);
			Calendar tweetCalendar = Calendar.getInstance();
			tweetCalendar.setTime(tweetDate);
			assertEquals(2010-1900, tweetDate.getYear() );
			assertEquals(Calendar.SUNDAY, tweetCalendar.get(Calendar.DAY_OF_WEEK) );
			assertEquals(Calendar.OCTOBER, tweetCalendar.get(Calendar.MONTH ));
			assertEquals(20, tweetCalendar.get(Calendar.HOUR_OF_DAY));
			assertEquals(56, tweetCalendar.get(Calendar.MINUTE ));
		} catch (RuntimeException e) {
			e.printStackTrace();
			fail("Date Parse Error");
		}
	}
	
	public void test_smart_date_differences() {
		Date d1 = Dates.newDate(2010, 1, 1, 10, 30, 0);
		Date d2 = Dates.newDate(2010, 1, 3, 11, 30, 0);
		assertEquals("2 days from now", Dates.differenceSmart(d1, d2));

		d2 = Dates.newDate(2010, 1, 1, 9, 29, 58);
		assertEquals("one hour ago", Dates.differenceSmart(d1, d2));

		d2 = Dates.newDate(2010, 1, 1, 12, 30, 5);
		assertEquals("2 hours from now", Dates.differenceSmart(d1, d2));

		d2 = Dates.newDate(2010, 1, 1, 10, 0, 0);
		assertEquals("30 minutes ago", Dates.differenceSmart(d1, d2));

		d2 = Dates.newDate(2010, 1, 1, 10, 29, 0);
		assertEquals("one minute ago", Dates.differenceSmart(d1, d2));

		d2 = Dates.newDate(2010, 1, 1, 10, 30, 0);
		assertEquals("now", Dates.differenceSmart(d1, d2));

		d2 = Dates.newDate(2010, 1, 1, 10, 29, 1);
		assertEquals("now", Dates.differenceSmart(d1, d2));

		d2 = Dates.newDate(2010, 1, 1, 10, 30, 5);
		assertEquals("now", Dates.differenceSmart(d1, d2));
	}
}
