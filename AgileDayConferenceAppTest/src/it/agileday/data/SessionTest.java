package it.agileday.data;

import it.agileday.utils.Dates;
import it.aglieday.data.Session;

import java.util.Date;

import junit.framework.TestCase;

public class SessionTest extends TestCase {
	public void test_validation() {
		Date d1 = Dates.newDate(2010, 1, 1, 10);
		Date d2 = Dates.newDate(2010, 1, 1, 11);

		assertEquals("End date must be greater than start date", new Session().setStart(d1).setEnd(d1).validationMessage());
		assertEquals("End date must be greater than start date", new Session().setStart(d2).setEnd(d1).validationMessage());
		assertTrue(new Session().setStart(d1).setEnd(d2).isValid());
	}

	public void test_toString() {
		assertEquals("Session#123", new Session().setId(123).toString());
		assertEquals("Session#0", new Session().toString());
	}
}
