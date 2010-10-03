package it.agileday.ui.speakers;

import it.agileday.R;
import it.aglieday.data.Dao;
import it.aglieday.data.DatabaseHelper;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ViewAnimator;

public class SpeakersActivity extends Activity {
	private ViewAnimator viewAnimator;
	private GestureDetector gestureDetector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speakers);

		Dao dao = new Dao(new DatabaseHelper(this).getReadableDatabase());
		Cursor speakers = dao.fetchAllSpeakers();
		startManagingCursor(speakers);

		this.gestureDetector = new GestureDetector(this, new GestureListener());

		viewAnimator = (ViewAnimator) findViewById(R.id.flipper);
		viewAnimator.setDisplayedChild(0);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
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
