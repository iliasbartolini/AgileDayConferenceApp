package it.agileday.utils;

import it.agileday.R;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ViewAnimator;

public class GestureListener extends SimpleOnGestureListener {
	private static final String TAG = GestureListener.class.getName();
	private static final float FLING_THRESHOLD = 500.0f;
	private final ViewAnimator viewAnimator;
	private final Context context;

	public GestureListener(Context context, ViewAnimator viewAnimator) {
		this.context = context;
		this.viewAnimator = viewAnimator;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		Log.d(TAG, Float.valueOf(velocityX).toString());
		boolean flingRight = velocityX > FLING_THRESHOLD;
		boolean flingLeft = velocityX < -FLING_THRESHOLD;
		int currentItem = viewAnimator.getDisplayedChild();
		boolean hasNextItem = (viewAnimator.getChildCount() - 1 > currentItem);
		boolean hasPrevItem = (currentItem > 0);

		if (flingRight && hasPrevItem) {
			viewAnimator.setOutAnimation(context, R.anim.slideout_toright);
			viewAnimator.setInAnimation(context, R.anim.slidein_fromleft);
			viewAnimator.setDisplayedChild(currentItem - 1);
		} else if (flingLeft && hasNextItem) {
			viewAnimator.setOutAnimation(context, R.anim.slideout_toleft);
			viewAnimator.setInAnimation(context, R.anim.slidein_fromright);
			viewAnimator.setDisplayedChild(currentItem + 1);
		}
		return true;
	}
}