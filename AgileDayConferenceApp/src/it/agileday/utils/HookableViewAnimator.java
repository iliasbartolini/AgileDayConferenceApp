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

package it.agileday.utils;

import it.agileday.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ViewAnimator;

public class HookableViewAnimator extends ViewAnimator implements ILeftRightAction  {
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


	public void goToLeft() {
		int currentItem = getDisplayedChild();
		boolean hasPrevItem = (currentItem > 0);

		if (hasPrevItem) {
			setOutAnimation(getContext(), R.anim.slideout_toright);
			setInAnimation(getContext(), R.anim.slidein_fromleft);
			setDisplayedChild(currentItem - 1);
		}
	}
	public void goToRight() {
		int currentItem = getDisplayedChild();
		boolean hasNextItem = (getChildCount() - 1 > currentItem);
		if (hasNextItem) {
			setOutAnimation(getContext(), R.anim.slideout_toleft);
			setInAnimation(getContext(), R.anim.slidein_fromright);
			setDisplayedChild(currentItem + 1);
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
