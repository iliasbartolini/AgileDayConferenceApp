package it.agileday.ui.speakers;

import it.agileday.R;
import it.aglieday.data.Dao;
import it.aglieday.data.DatabaseHelper;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Gallery;
import android.widget.SimpleCursorAdapter;

public class SpeakersActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speakers);

		Dao dao = new Dao(new DatabaseHelper(this).getReadableDatabase());
		Cursor speakers = dao.fetchAllSpeakers();
		startManagingCursor(speakers);

		Gallery g = (Gallery) findViewById(R.id.gallery);
		g.setAdapter(new SimpleCursorAdapter(this, R.layout.speakers_item, speakers, new String[] { "name", "bio" }, new int[] { R.id.name, R.id.bio }));
	}
}
