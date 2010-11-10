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

import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class SpeakerRepositoryTest extends AndroidTestCase {
	

	private SpeakerRepository m_speakerRepository;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		SQLiteDatabase db = new DatabaseHelper(getContext()).getReadableDatabase();
		m_speakerRepository = new SpeakerRepository(db, getContext().getResources(), null);
	}

	public void test_loading_all_speakers(){
		List<Speaker> speakers = m_speakerRepository.getAll();
		assertNotNull(speakers);
		assertTrue(speakers.size() > 0);
	}
	
}
