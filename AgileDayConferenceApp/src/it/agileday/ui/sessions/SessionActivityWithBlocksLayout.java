package it.agileday.ui.sessions;

import it.agileday.R;
import it.agileday.data.DatabaseHelper;
import it.agileday.data.Session;
import it.agileday.data.Track;
import it.agileday.data.TrackRepository;
import it.agileday.utils.Dates;

import java.util.ArrayList;

import com.google.android.apps.iosched.ui.widget.BlockView;
import com.google.android.apps.iosched.ui.widget.BlocksLayout;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

public class SessionActivityWithBlocksLayout extends Activity implements View.OnClickListener {
	private static final String TAG = SessionActivityWithBlocksLayout.class.getName();

	private static final int DISABLED_BLOCK_ALPHA = 100;

	private ScrollView mScrollView;
	private BlocksLayout mBlocks;
	private View mNowView;

	private long mTimeStart;
	private long mTimeEnd;

	private BroadcastReceiver mTimeChangesReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "onReceive time update");
			updateNowView(false);
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blocks_content);

		mTimeStart = Dates.newDate(2010, 11, 19, 8).getTime();
		mTimeEnd = Dates.newDate(2010, 11, 19, 19).getTime();

		mScrollView = (ScrollView) findViewById(R.id.blocks_scroll);
		mBlocks = (BlocksLayout) findViewById(R.id.blocks);
		mNowView = findViewById(R.id.blocks_now);

		mBlocks.setDrawingCacheEnabled(true);
		mBlocks.setAlwaysDrawnWithCacheEnabled(true);

		fillData();
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Since we build our views manually instead of using an adapter, we
		// need to manually requery every time launched.
		fillData();

		// Start listening for time updates to adjust "now" bar. TIME_TICK is
		// triggered once per minute, which is how we move the bar over time.
		final IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_TIME_TICK);
		filter.addAction(Intent.ACTION_TIME_CHANGED);
		filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
		registerReceiver(mTimeChangesReceiver, filter, null, new Handler());

		mNowView.post(new Runnable() {
			public void run() {
				updateNowView(true);
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mTimeChangesReceiver);
	}

	private void updateNowView(boolean forceScroll) {
		final long now = System.currentTimeMillis();

		final boolean visible = now >= mTimeStart && now <= mTimeEnd;
		mNowView.setVisibility(visible ? View.VISIBLE : View.GONE);

		if (visible && forceScroll) {
			// Scroll to show "now" in center
			final int offset = mScrollView.getHeight() / 2;
			mNowView.requestRectangleOnScreen(new Rect(0, offset, 0, offset), true);
		}

		mBlocks.requestLayout();
	}

	private void fillData() {
		SQLiteDatabase database = new DatabaseHelper(this).getReadableDatabase();
		try {
			ArrayList<Track> tracks = new TrackRepository(database, this).getAll();
			int i = 0;
			mBlocks.removeAllBlocks();

			for (Track track : tracks) {
				if (!track.isValid()) {
					throw new RuntimeException(track.validationMessage());
				}


//				final Integer column = i;
//				final String blockId = "someid" + i;
//				final String title = "Titolo!";
//				final long start = Dates.newDate(2010, 11, 19, 12+i).getTime();
//				final long end = Dates.newDate(2010, 11, 19, 14+i).getTime();
//				final boolean isStarred = true;
//
//				final BlockView blockView = new BlockView(this, blockId, title, start, end, isStarred, column);
//				blockView.setOnClickListener(this);
//				mBlocks.addBlock(blockView);				
				int j = 0;
				for (Session session : track.getSessions()) {
					final Integer column = i; // sTypeColumnMap.get()
					final String blockId = "session_"+track.order+"_"+j; // TODO:Fix
					final String title = session.title;
					final long start = session.getStart().getTime();
					final long end = session.getEnd().getTime();
					final boolean isStarred = session.IsStarred();

					final BlockView blockView = new BlockView(this, blockId, title, start, end, isStarred, column);

					if (!session.title.contains("break")) {
						blockView.setOnClickListener(this);
					} else {
						blockView.setFocusable(false);
						blockView.setEnabled(false);
						LayerDrawable buttonDrawable = (LayerDrawable) blockView.getBackground();
						buttonDrawable.getDrawable(0).setAlpha(DISABLED_BLOCK_ALPHA);
						buttonDrawable.getDrawable(2).setAlpha(DISABLED_BLOCK_ALPHA);
					}

					mBlocks.addBlock(blockView);
					j++;
				}

				// View view = buildView(R.layout.track, viewAnimator);
				// setText(view, R.id.title, track.title);
				// updateLeftRightTrackButton(view, R.id.previous_session, i);
				// updateLeftRightTrackButton(view, R.id.next_session, tracks.size() - (i + 1));
				//
				// ViewGroup sessionsViewGroup = (ViewGroup) view.findViewById(R.id.sessions);
				// for (Session session : track.getSessions()) {
				// sessionsViewGroup.addView(getSessionView(sessionsViewGroup, session, !track.hasNext(session)));
				// if (track.hasNext(session)) {
				// sessionsViewGroup.addView(buildView(R.layout.session_item_separator, sessionsViewGroup));
				// }
				// }
				// viewAnimator.addView(view);
				i++;
			}

		} finally {
			database.close();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	// private View buildView(int id, ViewGroup parent) {
	// return getLayoutInflater().inflate(id, parent, false);
	// }

}
