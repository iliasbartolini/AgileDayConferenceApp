package it.agileday.ui.speakers;

import it.agileday.R;
import it.aglieday.data.Dao;
import it.aglieday.data.DatabaseHelper;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.Gallery;
import android.widget.TextView;

public class SpeakersActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speakers);

		Dao dao = new Dao(new DatabaseHelper(this).getReadableDatabase());
		Cursor speakers = dao.fetchAllSpeakers();
		startManagingCursor(speakers);

		Gallery g = (Gallery) findViewById(R.id.gallery);
		g.setAdapter(new SpeakersCursorAdapter(this, speakers));
	}

	private class SpeakersCursorAdapter extends CursorAdapter {
		public SpeakersCursorAdapter(Context context, Cursor c) {
			super(context, c);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			TextView name = (TextView) view.findViewById(R.id.name);
			name.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
			TextView bio = (TextView) view.findViewById(R.id.bio);
			bio.setText(cursor.getString(cursor.getColumnIndexOrThrow("bio")));
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View ret = getLayoutInflater().inflate(R.layout.speakers_item, parent, false);
			bindView(ret, context, cursor);
			return ret;
		}
	}
}
