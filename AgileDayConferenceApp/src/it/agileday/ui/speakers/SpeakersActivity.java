package it.agileday.ui.speakers;

import it.agileday.R;
import it.aglieday.data.Dao;
import it.aglieday.data.DatabaseHelper;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ViewFlipper;

public class SpeakersActivity extends Activity {
	private ViewFlipper vf;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speakers);

		Dao dao = new Dao(new DatabaseHelper(this).getReadableDatabase());
		Cursor speakers = dao.fetchAllSpeakers();
		startManagingCursor(speakers);

		// Gallery g = (Gallery) findViewById(R.id.gallery);
		// g.setAdapter(new SpeakersCursorAdapter(this, speakers));

		vf = (ViewFlipper) findViewById(R.id.flipper);
		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		vf.setOnTouchListener(new TouchListener(vf, display.getWidth()));
		vf.setDisplayedChild(1);

	}

	private static class TouchListener implements OnTouchListener {
		private static final String TAG = TouchListener.class.getName();
		private final ViewFlipper viewFlipper;
		private float downXValue;
		private final int displayWidth;

		public TouchListener(ViewFlipper viewFlipper, int displayWidth) {
			this.viewFlipper = viewFlipper;
			this.displayWidth = displayWidth;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			final View leftView = viewFlipper.getChildAt(viewFlipper.getDisplayedChild() - 1);
			final View centerView = viewFlipper.getCurrentView();
			final View rightView = viewFlipper.getChildAt(viewFlipper.getDisplayedChild() + 1);
			final float currentX = event.getX();
			final boolean isRightDrag = downXValue < currentX;
			final boolean isLeftDrag = downXValue > currentX;
			final float xOffset = Math.abs(currentX - downXValue);
			final float normalizedXOffset = xOffset / displayWidth;
			final int action = event.getAction();

			Log.d(TAG, "Touch action " + action + " on x=" + currentX + ", downXValue=" + downXValue);
			if (action == MotionEvent.ACTION_DOWN) {
				downXValue = event.getX();
			} else if (action == MotionEvent.ACTION_MOVE) {
				if (isLeftDrag && rightView != null) {
					setViewXOffset(centerView, (int) -xOffset);
					setViewXOffset(rightView, displayWidth - (int) xOffset);
					rightView.setVisibility(View.VISIBLE);
				} else if (isRightDrag && leftView != null) {
					setViewXOffset(centerView, (int) xOffset);
					setViewXOffset(leftView, (int) xOffset - displayWidth);
					leftView.setVisibility(View.VISIBLE);
				}
			} else if (action == MotionEvent.ACTION_UP) {
				if (isRightDrag && leftView != null) {
					this.viewFlipper.setInAnimation(buildTranslateAnimation(normalizedXOffset - 1.0f, 0.0f));
					this.viewFlipper.setOutAnimation(buildTranslateAnimation(0.0f, 1.0f - normalizedXOffset));
					viewFlipper.showPrevious();
				} else if (isLeftDrag && rightView != null) {
					this.viewFlipper.setInAnimation(buildTranslateAnimation(1.0f - normalizedXOffset, 0.0f));
					this.viewFlipper.setOutAnimation(buildTranslateAnimation(0.0f, normalizedXOffset - 1.0f));
					viewFlipper.showNext();
				}
			}
			return true;
		}

		private void setViewXOffset(View view, int offset) {
			view.layout(offset, view.getTop(), view.getRight(), view.getBottom());
		}

		private TranslateAnimation buildTranslateAnimation(float fromXValue, float toXValue) {
			TranslateAnimation ret = new TranslateAnimation(
					Animation.RELATIVE_TO_PARENT, fromXValue,
					Animation.RELATIVE_TO_PARENT, toXValue,
					Animation.RELATIVE_TO_PARENT, 0.0f,
					Animation.RELATIVE_TO_PARENT, 0.0f);
			ret.setDuration(250);
			ret.setInterpolator(new AccelerateDecelerateInterpolator());
			return ret;
		}
	}

}
