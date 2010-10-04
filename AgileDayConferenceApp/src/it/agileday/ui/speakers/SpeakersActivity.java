package it.agileday.ui.speakers;

import it.agileday.R;
import it.aglieday.data.DatabaseHelper;
import it.aglieday.data.Speaker;
import it.aglieday.data.SpeakerRepository;

import java.util.List;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewAnimator;

public class SpeakersActivity extends Activity {
	private static final String TAG = SpeakersActivity.class.getName();
	private ViewAnimator viewAnimator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speakers);
		viewAnimator = (ViewAnimator) findViewById(R.id.flipper);

		SQLiteDatabase database = new DatabaseHelper(this).getReadableDatabase();
		SpeakerRepository repo = new SpeakerRepository(database);
		List<Speaker> speakers = repo.getAll();
		for (Speaker speaker : speakers) {
			View view = getLayoutInflater().inflate(R.layout.speakers_item, viewAnimator, false);
			TextView name = (TextView) view.findViewById(R.id.name);
			name.setText(speaker.name);
			TextView bio = (TextView) view.findViewById(R.id.bio);
			setHtmlText(speaker, bio);

			viewAnimator.addView(view);
		}
		database.close();
		viewAnimator.setDisplayedChild(2);
	}

	private void setHtmlText(Speaker speaker, TextView bio) {
		bio.setMovementMethod(LinkMovementMethod.getInstance()); // http://code.google.com/p/android/issues/detail?id=2219
		bio.setClickable(false);
		bio.setFocusable(false);
		bio.setFocusableInTouchMode(false);
		bio.setLongClickable(false);
		bio.setText(Html.fromHtml(speaker.bio));
	}
}
