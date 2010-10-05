package it.agileday.ui.speakers;

import it.agileday.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ViewAnimator;

public class FlingViewAnimator extends ViewAnimator {
	private GestureDetector gestureDetector;

	public FlingViewAnimator(Context context) {
		super(context);
		init(context);
	}

	public FlingViewAnimator(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		gestureDetector = new GestureDetector(context, new GestureListener(this));
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return gestureDetector.onTouchEvent(ev);
	}

	private class GestureListener extends SimpleOnGestureListener {
		private final ViewAnimator viewAnimator;

		public GestureListener(ViewAnimator viewAnimator) {
			this.viewAnimator = viewAnimator;
		}

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