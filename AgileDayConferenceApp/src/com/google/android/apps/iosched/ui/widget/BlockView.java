/*
	Copyright 2010

	Author: Google Inc.
	Author: Ilias Bartolini

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

package com.google.android.apps.iosched.ui.widget;

import it.agileday2011.R;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.util.TypedValue;
import android.widget.Button;

/**
 * Custom view that represents a {@link Blocks#BLOCK_ID} instance, including its title and time span that it occupies. 
 * Usually organized automatically by {@link BlocksLayout} to match up against a {@link TimeRulerView} instance.
 */
public class BlockView extends Button {
	private final long mBlockId;
	private final String mTitle;
	private final long mStartTime;
	private final long mEndTime;
	private final boolean mContainsStarred;
	private final int mColumn;

	public BlockView(Context context, long blockId, String title, long startTime, long endTime, boolean containsStarred, int column, int buttonColor, int textColor) {
		super(context);

		mBlockId = blockId;
		mTitle = title;
		mStartTime = startTime;
		mEndTime = endTime;
		mContainsStarred = containsStarred;
		mColumn = column;

		setText(mTitle);
		LayerDrawable buttonDrawable = (LayerDrawable) context.getResources().getDrawable(R.drawable.btn_block);
		buttonDrawable.getDrawable(0).setColorFilter(buttonColor, PorterDuff.Mode.SRC_ATOP);
		buttonDrawable.getDrawable(1).setAlpha(mContainsStarred ? 255 : 0);

		setTextColor(textColor);
		setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
		setBackgroundDrawable(buttonDrawable);
	}

	public long getBlockId() {
		return mBlockId;
	}

	public long getStartTime() {
		return mStartTime;
	}

	public long getEndTime() {
		return mEndTime;
	}

	public int getColumn() {
		return mColumn;
	}
}
