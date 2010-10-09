package it.aglieday.data;

import it.agileday.R;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = DatabaseHelper.class.getName();
	private static final String DATABASE_NAME = "data.db";
	private static final int DATABASE_VERSION = 15;
	private final Context context;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		InputStream stream = context.getResources().openRawResource(R.raw.database);
		execScript(stream, db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Found database version " + oldVersion + ". Rebuilding database version " + newVersion);
		onCreate(db);
	}

	private static void execScript(InputStream stream, SQLiteDatabase db) {
		Scanner scanner;
		try {
			scanner = new Scanner(new InputStreamReader(stream, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		scanner.useDelimiter(";\\s*(\\n|\\r\\n|\\r)");
		while (scanner.hasNext()) {
			String sql = scanner.next();
			db.execSQL(sql);
		}
	}
}