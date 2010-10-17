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
package it.agileday.data;

import it.agileday.utils.Dates;

import java.util.Date;

import junit.framework.TestCase;

public class TrackTest extends TestCase {
	private Date d1 = Dates.newDate(2010, 1, 1, 10);
	private Date d2 = Dates.newDate(2010, 1, 1, 11);
	private Date d3 = Dates.newDate(2010, 1, 1, 12);
	private Date d4 = Dates.newDate(2010, 1, 1, 13);
	private Date d5 = Dates.newDate(2010, 1, 1, 14);

	public void setUp() {
	}

	public void test_track_with_invalid_session_is_invalid() {
		Track target = new Track()
				.addSession(new Session().setId(1).setStart(d2).setEnd(d1));
		assertEquals("Session#1: End date must be greater than start date", target.validationMessage());
	}

	public void test_track_with_overlapping_session_is_invalid() {
		Track target = new Track()
				.addSession(new Session().setStart(d1).setEnd(d3))
				.addSession(new Session().setStart(d2).setEnd(d4));
		assertEquals("Session overlap/bad order/hole detected", target.validationMessage());
	}

	public void test_track_with_hole_is_invalid() {
		Track target = new Track()
				.addSession(new Session().setStart(d1).setEnd(d2))
				.addSession(new Session().setStart(d4).setEnd(d5));
		assertEquals("Session overlap/bad order/hole detected", target.validationMessage());
	}

	public void test_valid_track_cases() {
		Track target = new Track()
				.addSession(new Session().setStart(d1).setEnd(d2))
				.addSession(new Session().setStart(d2).setEnd(d3));
		assertTrue(target.isValid());
	}

	public void test_hasNext_hasPrevious() {
		Session first = new Session().setStart(d1).setEnd(d2);
		Session last = new Session().setStart(d2).setEnd(d3);
		Track target = new Track()
				.addSession(first)
				.addSession(last);
		assertFalse(target.hasPrevious(first));
		assertTrue(target.hasNext(first));
		assertTrue(target.hasPrevious(last));
		assertFalse(target.hasNext(last));
	}
}
