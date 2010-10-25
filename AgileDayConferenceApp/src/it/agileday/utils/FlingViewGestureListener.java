/*
	Copyright 2010 
	
	Author: Gian Marco Gherardi

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

import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class FlingViewGestureListener extends SimpleOnGestureListener {
	private static final String TAG = FlingViewGestureListener.class.getName();
	private static final float FLING_THRESHOLD = 500.0f;
	private final ILeftRightAction action;

	public FlingViewGestureListener(ILeftRightAction action) {
		this.action = action;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		Log.d(TAG, Float.valueOf(velocityX).toString());
		boolean flingRight = velocityX > FLING_THRESHOLD;
		boolean flingLeft = velocityX < -FLING_THRESHOLD;
		
		if (flingLeft){
			action.goToRight();
		} else if (flingRight){
			action.goToLeft();
		}
		return true;
	}
}