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

import java.util.ArrayList;
import java.util.Iterator;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class TrackRepositoryTest extends AndroidTestCase {

	private TrackRepository m_trackRepository;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		SQLiteDatabase db = new DatabaseHelper(getContext()).getReadableDatabase();
		m_trackRepository = new TrackRepository(db, null);
	}

	public void test_loading_all_tracks(){
		ArrayList<Track> tracks = m_trackRepository.getAll();
		assertNotNull(tracks);
		assertTrue(tracks.size() > 0);
	}

	public void test_loading_all_tracks_list_must_be_ordered(){
		ArrayList<Track> tracks = m_trackRepository.getAll();
		
		Iterator<Track> iterator = tracks.iterator();
		Track previous = iterator.next();
		while (iterator.hasNext()) {
			Track current = iterator.next();
			assertTrue(current.compareTo(previous) > 0);
			previous = current;
		}
	}
	

}
