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

import it.agileday.utils.Dates;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SessionRepository {

	private final SQLiteDatabase db;
	private final Activity activity;	

	public SessionRepository(SQLiteDatabase db, Activity activityToManageCursorLifeCicle) {
		this.db = db;
		this.activity = activityToManageCursorLifeCicle;
	}

	public Session getSessionFromId(long sessionId) {

		String sql = "SELECT sessions._id AS session_id, sessions.session_type AS session_type, sessions.title AS session_title, sessions.start AS session_start, sessions.end AS session_end FROM sessions WHERE sessions._id = " + sessionId;
		Cursor cursor = db.rawQuery(sql, null);
		if (activity != null)
		{
			activity.startManagingCursor(cursor);
		}

		Session session = new Session();
		if (cursor.moveToNext()) {
			session = buildSession(cursor);
		}
		cursor.close();

		return session;
	}

	public static Session buildSession(Cursor c) {
		Session ret = new Session();
		ret.setId(c.getInt(c.getColumnIndexOrThrow("session_id")));
		ret.type = c.getString(c.getColumnIndexOrThrow("session_type"));
		ret.title = c.getString(c.getColumnIndexOrThrow("session_title"));
		ret.setStart(Dates.fromDbString(c.getString(c.getColumnIndexOrThrow("session_start"))));
		ret.setEnd(Dates.fromDbString(c.getString(c.getColumnIndexOrThrow("session_end"))));
		ret.speakers = "Pinco Pallino & Gertrude";
		ret.description = 
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a><br><br>" +
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a><br><br>" +
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a><br><br>" +
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a><br><br>" +
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a><br><br>" +
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a><br><br>" +
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a>" +
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a><br><br>" +
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a><br><br>" +
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a><br><br>" +
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a><br><br>" +
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a><br><br>" +
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a>" +
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a><br><br>" +
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a><br><br>" +
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a>" +
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a>" +
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a><br><br>" +
			"Bla Bla Bla ... solo minchiate <b>in grassetto</b><a href=\"http://www.example.com\">link</a><br><br>";
		return ret;
	}	
	
}
