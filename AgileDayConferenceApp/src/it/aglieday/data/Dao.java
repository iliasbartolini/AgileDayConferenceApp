package it.aglieday.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Dao {
	static final String TAG = Dao.class.getName();
	private SQLiteDatabase db;

	public Dao(SQLiteDatabase database) {
		this.db = database;
	}

	public long createNote(String title, String body) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("title", title);
		initialValues.put("body", body);

		return db.insert("notes", null, initialValues);
	}

	public Cursor fetchAllSpeakers() {
		return db.query("speakers", new String[] { "_id", "name", "bio" }, null, null, null, null, null);
	}

	public Cursor fetchSpeaker(long rowId) throws SQLException {
		Cursor ret = db.query(true, "speakers", new String[] { "_id", "name", "bio" }, "_id" + "=" + rowId, null, null, null, null, null);
		if (ret != null) {
			ret.moveToFirst();
		}
		return ret;
	}
}
