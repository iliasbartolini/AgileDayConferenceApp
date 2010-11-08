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

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SpeakerRepository {
	static final String TAG = SpeakerRepository.class.getName();
	private SQLiteDatabase db;
	private final Resources resources;
	private final Activity activity;

	public SpeakerRepository(SQLiteDatabase database, Resources imageResources, Activity activityToManageCursorLifeCicle) {
		this.db = database;
		this.resources = imageResources;
		this.activity = activityToManageCursorLifeCicle;
	}

	public long createNote(String title, String body) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("title", title);
		initialValues.put("body", body);

		return db.insert("notes", null, initialValues);
	}

	public List<Speaker> getAll() {
		List<Speaker> ret = new ArrayList<Speaker>();
		Cursor cursor = db.query("speakers", new String[] { "_id", "name", "bio", "image" }, null, null, null, null, "Name");
		if (activity != null)
		{
			activity.startManagingCursor(cursor);
		}
		while (cursor.moveToNext()) {
			ret.add(buildSpeaker(cursor));
		}
		return ret;
	}

	private Speaker buildSpeaker(Cursor cursor) {
		Speaker ret = new Speaker();
		ret.name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
		ret.bio = cursor.getString(cursor.getColumnIndexOrThrow("bio"));
		String imageName = cursor.getString(cursor.getColumnIndexOrThrow("image"));
		int resourceId = resources.getIdentifier("speaker_"+imageName, "drawable", "it.agileday");
		//TODO: manage missing resource
		ret.image = resources.getDrawable(resourceId);
		return ret;
	}
}
