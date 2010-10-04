package it.agileday.ui.speakers;

import it.agileday.R;
import it.aglieday.data.DatabaseHelper;
import it.aglieday.data.Speaker;
import it.aglieday.data.SpeakerRepository;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
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

		SpeakerRepository repo = new SpeakerRepository(new DatabaseHelper(this).getReadableDatabase());
		List<Speaker> speakers = repo.getAll();
		for (Speaker speaker : speakers) {
			View view = getLayoutInflater().inflate(R.layout.speakers_item, viewAnimator, false);
			TextView name = (TextView) view.findViewById(R.id.name);
			name.setText(speaker.name);
			TextView bio = (TextView) view.findViewById(R.id.bio);
			bio.setMovementMethod(LinkMovementMethod.getInstance()); // http://code.google.com/p/android/issues/detail?id=2219
			bio.setText(Html.fromHtml(speaker.bio));
			viewAnimator.addView(view);
		}

		gestureDetector = new GestureDetector(this, new GestureListener());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "Touch view");
		return gestureDetector.onTouchEvent(event);
	}

	private Context getContext() {
		return this;
	}

	private class GestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			boolean flingRight = velocityX > 0;
			boolean flingLeft = velocityX < 0;
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

}
