package it.agileday.utils;

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
		Date d1 = Dates.newDate(2010, 1, 1, 10, 0, 0);
		assertEquals("2010-01-01 10:00:00", Dates.toString(d1));
		
		assertTrue(Dates.equal(d1, Dates.fromString("2010-01-01 10:00:00")));
		
		try {
			Dates.fromString("today");
			fail();
		} catch (Exception e) {
			assertEquals("Cannot parse 'today' as date", e.getMessage());
		}
	}
}
