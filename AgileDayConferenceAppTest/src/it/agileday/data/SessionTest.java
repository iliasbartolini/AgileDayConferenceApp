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
import it.agileday.data.Session;

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
