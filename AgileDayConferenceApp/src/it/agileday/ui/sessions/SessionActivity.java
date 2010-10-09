/*
   Copyright 2010 Ilias Bartolini, Luigi Bozzo, Andrea Chiarini

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

package it.agileday.ui.sessions;

import it.agileday.R;
import it.agileday.utils.GestureListener;
import it.aglieday.data.DatabaseHelper;
import it.aglieday.data.Track;
import it.aglieday.data.TrackRepository;

import java.util.Collection;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewAnimator;

public class SessionActivity extends Activity {
	@SuppressWarnings("unused")
	private static final String TAG = SessionActivity.class.getName();
	private ViewAnimator viewAnimator;
	private GestureDetector gestureDetector;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sessions);
		viewAnimator = (ViewAnimator) findViewById(R.id.flipper);
		gestureDetector = new GestureDetector(this, new GestureListener(this, viewAnimator));
		fillData();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return gestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
	}

	private void fillData() {
		SQLiteDatabase database = new DatabaseHelper(this).getReadableDatabase();
		try {
			Collection<Track> tracks = new TrackRepository(database).getAll();
			for (Track track : tracks) {
				if (!track.isValid()) {
					throw new RuntimeException(track.validationMessage());
				}
				View view = getLayoutInflater().inflate(R.layout.track, viewAnimator, false);
				TextView title = (TextView) view.findViewById(R.id.title);
				title.setText(track.title);
				ListView listView = (ListView) view.findViewById(R.id.list);
				listView.setAdapter(new SessionAdapter(this, track));
				viewAnimator.addView(view);
			}
		} finally {
			database.close();
		}
	}
}
