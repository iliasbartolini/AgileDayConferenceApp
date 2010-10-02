package it.aglieday.data;

import it.agileday.R;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = DatabaseHelper.class.getName();
	private static final String DATABASE_NAME = "data";
	private static final int DATABASE_VERSION = 2;
	private final Context context;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = toString(context.getResources().openRawResource(R.raw.database));
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Found database version " + oldVersion + ". Rebuilding database version " + newVersion);
		onCreate(db);
	}

	private static String toString(InputStream in) {
		try {
			StringBuffer ret = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = in.read(b)) != -1;) {
				ret.append(new String(b, 0, n));
			}
			return ret.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}