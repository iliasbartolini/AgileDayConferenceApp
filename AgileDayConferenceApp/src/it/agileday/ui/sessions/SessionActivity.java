/*
   Copyright 2010 Ilias Bartolini, Luigi Bozzo, Andrea Chiarini

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

package it.agileday.ui.sessions;

import it.agileday.R;
import it.agileday.utils.Dates;
import it.agileday.utils.FlingViewGestureListener;
import it.agileday.utils.HookableViewAnimator;
import it.agileday.data.DatabaseHelper;
import it.agileday.data.Session;
import it.agileday.data.Track;
import it.agileday.data.TrackRepository;

import java.util.Collection;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class SessionActivity extends Activity {
	@SuppressWarnings("unused")
	private static final String TAG = SessionActivity.class.getName();
	private static final String TIME_FORMAT = "H:mm";
	private static final float DIP_PER_MINUTE = 1.0f;

	private HookableViewAnimator viewAnimator;
	private GestureDetector gestureDetector;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sessions);
		viewAnimator = (HookableViewAnimator) findViewById(R.id.flipper);
		viewAnimator.setOnChildDisplayingListener(new OnChildDisplayingListener());
		gestureDetector = new GestureDetector(this, new FlingViewGestureListener(this, viewAnimator));
		fillData();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return gestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
	}

	private void fillData() {
		SQLiteDatabase database = new DatabaseHelper(this).getReadableDatabase();
		try {
			Collection<Track> tracks = new TrackRepository(database).getAll();
			for (Track track : tracks) {
				if (!track.isValid()) {
					throw new RuntimeException(track.validationMessage());
				}
				View view = buildView(R.layout.track, viewAnimator);
				setText(view, R.id.title, track.title);
				ViewGroup sessionsViewGroup = (ViewGroup) view.findViewById(R.id.sessions);
				for (Session session : track.getSessions()) {
					sessionsViewGroup.addView(getSessionView(sessionsViewGroup, session));
				}
				viewAnimator.addView(view);
			}
		} finally {
			database.close();
		}
	}

	public View getSessionView(ViewGroup parent, Session session) {
		View ret = buildView(R.layout.sessions_item, parent);
		setSessionViewHeight(ret, session);
		setText(ret, R.id.title, session.title);
		setText(ret, R.id.start, Dates.toString(session.getStart(), TIME_FORMAT));
		setText(ret, R.id.end, Dates.toString(session.getEnd(), TIME_FORMAT));
		return ret;
	}

	private void setSessionViewHeight(View view, Session session) {
		float scale = getResources().getDisplayMetrics().density;
		int height = (int) (Dates.differenceMinutes(session.getEnd(), session.getStart()) * scale * DIP_PER_MINUTE);
		view.getLayoutParams().height = height;
	}

	private void setText(View view, int id, String text) {
		TextView txt = (TextView) view.findViewById(id);
		txt.setText(text);
	}

	private View buildView(int id, ViewGroup parent) {
		return getLayoutInflater().inflate(id, parent, false);
	}

	private class OnChildDisplayingListener implements HookableViewAnimator.OnChildDisplayingListener {
		@Override
		public boolean onChildDisplayedChanging(HookableViewAnimator sender, int newChild) {
			ScrollView oldView = (ScrollView) sender.getChildAt(sender.getDisplayedChild()).findViewById(R.id.scroll);
			ScrollView newView = (ScrollView) sender.getChildAt(newChild).findViewById(R.id.scroll);
			newView.scrollTo(newView.getScrollX(), oldView.getScrollY());
			return true;
		}

		@Override
		public void onChildDisplayedChanged(HookableViewAnimator sender, int newChild) {
		}
	}

//	private class MyGestureListener extends GestureListener{
//
//		public MyGestureListener(Context context, ViewAnimator viewAnimator) {
//			super(context, viewAnimator);
//		}
//
//		@Override
//		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//			boolean ret = super.onFling(e1, e2, velocityX, velocityY);
//			if(ret) {
//				View view = viewAnimator.getChildAt(viewAnimator.getDisplayedChild());
//				
//				ScrollView sv = null;
//				
//				sv.scrollTo(sv.getScrollX(), sv.getScrollY());
//				sessionsViewGroup.getTop();
//			}
//			return ret;
//		}
//		
//		
//		
//	}

}
