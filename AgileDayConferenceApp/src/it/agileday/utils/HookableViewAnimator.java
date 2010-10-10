package it.agileday.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewAnimator;

public class HookableViewAnimator extends ViewAnimator {
	private OnChildDisplayingListener listener = null;

	public HookableViewAnimator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HookableViewAnimator(Context context) {
		super(context);
	}

	@Override
	public void setDisplayedChild(int whichChild) {
		if (listener != null && !listener.onChildDisplayedChanging(this, whichChild)) {
			return;
		}
		super.setDisplayedChild(whichChild);
		if (listener != null) {
			listener.onChildDisplayedChanged(this, whichChild);
		}
	}

	public void setOnChildDisplayingListener(OnChildDisplayingListener l) {
		this.listener = l;
	}

	public interface OnChildDisplayingListener {
		boolean onChildDisplayedChanging(HookableViewAnimator sender, int newChild);

		void onChildDisplayedChanged(HookableViewAnimator sender, int newChild);
	}
}
