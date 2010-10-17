/*
	Copyright 2010 Italian Agile Day

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

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

	// Should reflect java.util.Comparator specification
	public void test_should_returns_a_negative_integer_if_the_first_argument_is_less_than_the_second() {
		Date d1 = Dates.newDate(2010, 1, 1, 10, 0, 0);
		Date d2 = Dates.newDate(2010, 1, 1, 10, 0, 1);
		assertTrue(Dates.differenceSeconds(d1, d2) < 0);
	}

	// Should reflect java.util.Comparator specification
	public void test_should_returns_a_positive_integer_as_the_first_argument_is_greater_than_the_second() {
		Date d1 = Dates.newDate(2010, 1, 1, 10, 0, 1);
		Date d2 = Dates.newDate(2010, 1, 1, 10, 0, 0);
		assertTrue(Dates.differenceSeconds(d1, d2) > 0);
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

	public void test_parse_twitter_dates() {
		String sampleDate = "Sun, 10 Oct 2010 20:56:58 +0002";
		try {
			Date tweetDate = Dates.fromTweeterString(sampleDate);
			Calendar tweetCalendar = Calendar.getInstance();
			tweetCalendar.setTime(tweetDate);
			assertEquals(2010, tweetCalendar.get(Calendar.YEAR));
			assertEquals(Calendar.SUNDAY, tweetCalendar.get(Calendar.DAY_OF_WEEK));
			assertEquals(Calendar.OCTOBER, tweetCalendar.get(Calendar.MONTH));
			assertEquals(20, tweetCalendar.get(Calendar.HOUR_OF_DAY));
			assertEquals(56, tweetCalendar.get(Calendar.MINUTE));
		} catch (RuntimeException e) {
			e.printStackTrace();
			fail("Date Parse Error");
		}
	}

	public void failingtest_parse_twitter_dates_with_timezone() {
		String sampleDate = "Sun, 10 Oct 2010 20:56:58 +0002";
		try {
			Date tweetDate = Dates.fromTweeterString(sampleDate);
			Calendar tweetCalendar = Calendar.getInstance();
			tweetCalendar.setTime(tweetDate);
			assertEquals(2, tweetCalendar.get(Calendar.ZONE_OFFSET));
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
