/*
	Copyright 2010 
	
	Author: Gian Marco Gherardi

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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TrackRepository {
	private final SQLiteDatabase db;

	public TrackRepository(SQLiteDatabase db) {
		this.db = db;
	}

	public Collection<Track> getAll() {
		Map<Integer, Track> ret = new HashMap<Integer, Track>();
		String sql = "SELECT track_id, tracks.title AS track_title, sessions.title AS session_title, sessions.start AS session_start, sessions.end AS session_end FROM sessions JOIN tracks ON(sessions.track_id = tracks._id) ORDER BY track_id, sessions.start";
		Cursor cursor = db.rawQuery(sql, null);

		while (cursor.moveToNext()) {
			getTrack(ret, cursor).addSession(buildSession(cursor));
		}
		return ret.values();
	}

	private Track getTrack(Map<Integer, Track> ret, Cursor cursor) {
		int trackId = cursor.getInt(cursor.getColumnIndexOrThrow("track_id"));
		if (!ret.containsKey(trackId)) {
			ret.put(trackId, buildTrack(cursor));
		}
		return ret.get(trackId);
	}

	private Track buildTrack(Cursor c) {
		Track ret = new Track();
		ret.title = c.getString(c.getColumnIndexOrThrow("track_title"));
		return ret;
	}

	private Session buildSession(Cursor c) {
		Session ret = new Session();
		ret.title = c.getString(c.getColumnIndexOrThrow("session_title"));
		ret.setStart(Dates.fromDbString(c.getString(c.getColumnIndexOrThrow("session_start"))));
		ret.setEnd(Dates.fromDbString(c.getString(c.getColumnIndexOrThrow("session_end"))));
		return ret;
	}
}
