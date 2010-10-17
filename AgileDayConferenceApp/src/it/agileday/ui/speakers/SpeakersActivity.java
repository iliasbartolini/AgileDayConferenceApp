/*
	Copyright 2010 Italian Agile Day

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

package it.agileday.ui.speakers;

import it.agileday.R;
import it.agileday.utils.FlingViewGestureListener;
import it.agileday.data.DatabaseHelper;
import it.agileday.data.Speaker;
import it.agileday.data.SpeakerRepository;

import java.util.List;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

public class SpeakersActivity extends Activity {
	static final String TAG = SpeakersActivity.class.getName();
	private ViewAnimator viewAnimator;
	private GestureDetector gestureDetector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speakers);
		viewAnimator = (ViewAnimator) findViewById(R.id.flipper);
		gestureDetector = new GestureDetector(this, new FlingViewGestureListener(this, viewAnimator));
		fillData();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return gestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
	}

	private void fillData() {
		SQLiteDatabase database = new DatabaseHelper(this).getReadableDatabase();
		try {
			SpeakerRepository repo = new SpeakerRepository(database, this);
			List<Speaker> speakers = repo.getAll();
			for (Speaker speaker : speakers) {
				View view = getLayoutInflater().inflate(R.layout.speakers_item, viewAnimator, false);
				TextView name = (TextView) view.findViewById(R.id.name);
				name.setText(speaker.name);
				TextView bio = (TextView) view.findViewById(R.id.bio);
				setHtmlText(speaker, bio);
				ImageView image = (ImageView) view.findViewById(R.id.image);
				image.setImageDrawable(speaker.image);
				viewAnimator.addView(view);
			}
		} finally {
			database.close();
		}
	}

	private void setHtmlText(Speaker speaker, TextView bio) {
		bio.setMovementMethod(LinkMovementMethod.getInstance()); // http://code.google.com/p/android/issues/detail?id=2219
		bio.setClickable(true);
		bio.setFocusable(false);
		bio.setFocusableInTouchMode(false);
		bio.setLongClickable(false);
		bio.setText(Html.fromHtml(speaker.bio));
	}
}
