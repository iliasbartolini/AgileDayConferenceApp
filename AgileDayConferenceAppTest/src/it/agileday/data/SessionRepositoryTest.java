/*
	Copyright 2010 
	
	Author: Ilias Bartolini

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

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class SessionRepositoryTest extends AndroidTestCase {

	private SessionRepository m_repo;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		SQLiteDatabase db = new DatabaseHelper(getContext()).getReadableDatabase();
		m_repo = new SessionRepository(db, null);
	}

	public void test_loading_a_session() {
		final int sessionId = 1;

		Session session = m_repo.getSessionFromId(sessionId);
		assertNotNull(session);
		assertEquals(sessionId, session.getId());
	}

	public void test_loading_an_unavailble_session() {
		final int sessionId = -2;

		Session session = m_repo.getSessionFromId(sessionId);
		assertNotNull(session);
		assertEquals(0, session.getId());
		assertEquals("", session.type);
	}

}
