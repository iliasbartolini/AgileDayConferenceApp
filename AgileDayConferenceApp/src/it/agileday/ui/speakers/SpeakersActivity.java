package it.agileday.ui.speakers;

import it.agileday.R;
import it.aglieday.data.DatabaseHelper;
import it.aglieday.data.Speaker;
import it.aglieday.data.SpeakerRepository;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

public class SpeakersActivity extends Activity {
	private static final String TAG = SpeakersActivity.class.getName();
	private ViewAnimator viewAnimator;
	private GestureDetector gestureDetector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speakers);
		viewAnimator = (ViewAnimator) findViewById(R.id.flipper);
		gestureDetector = new GestureDetector(this, new GestureListener());
		fillData();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return gestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
	}

	private Context getContext() {
		return this;
	}

	private class GestureListener extends SimpleOnGestureListener {
		private final float FLING_THRESHOLD = 500.0f;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			Log.d(TAG, Float.valueOf(velocityX).toString());
			boolean flingRight = velocityX > FLING_THRESHOLD;
			boolean flingLeft = velocityX < -FLING_THRESHOLD;
			int currentItem = viewAnimator.getDisplayedChild();
			boolean hasNextItem = (viewAnimator.getChildCount() - 1 > currentItem);
			boolean hasPrevItem = (currentItem > 0);

			if (flingRight && hasPrevItem) {
				viewAnimator.setOutAnimation(getContext(), R.anim.slideout_toright);
				viewAnimator.setInAnimation(getContext(), R.anim.slidein_fromleft);
				viewAnimator.setDisplayedChild(currentItem - 1);
			} else if (flingLeft && hasNextItem) {
				viewAnimator.setOutAnimation(getContext(), R.anim.slideout_toleft);
				viewAnimator.setInAnimation(getContext(), R.anim.slidein_fromright);
				viewAnimator.setDisplayedChild(currentItem + 1);
			}
			return true;
		}
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
