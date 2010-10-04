package it.aglieday.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SpeakerRepository {
	static final String TAG = SpeakerRepository.class.getName();
	private SQLiteDatabase db;

	public SpeakerRepository(SQLiteDatabase database) {
		this.db = database;
	}

	public long createNote(String title, String body) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("title", title);
		initialValues.put("body", body);

		return db.insert("notes", null, initialValues);
	}

	public List<Speaker> getAll() {
		List<Speaker> ret = new ArrayList<Speaker>();
		Cursor cursor = db.query("speakers", new String[] { "_id", "name", "bio" }, null, null, null, null, null);
		while (cursor.moveToNext()) {
			ret.add(buildSpeaker(cursor));
		}
		return ret;
	}

	private Speaker buildSpeaker(Cursor cursor) {
		Speaker ret = new Speaker();
		ret.name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
		ret.bio = cursor.getString(cursor.getColumnIndexOrThrow("bio"));
		return ret;
	}
}
