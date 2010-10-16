package it.agileday.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
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
		View oldChild = getChildAt(getDisplayedChild());
		View newChild = getChildAt(whichChild);
		if (listener != null && !listener.onChildDisplayedChanging(this, oldChild, newChild)) {
			return;
		}
		super.setDisplayedChild(whichChild);
		if (listener != null) {
			listener.onChildDisplayedChanged(this, oldChild, newChild);
		}
	}

	public void setOnChildDisplayingListener(OnChildDisplayingListener l) {
		this.listener = l;
	}

	public interface OnChildDisplayingListener {
		boolean onChildDisplayedChanging(HookableViewAnimator sender, View oldChild, View newChild);

		void onChildDisplayedChanged(HookableViewAnimator sender, View oldChild, View newChild);
	}
}
