package it.agileday.ui.sessions;

import it.agileday.R;
import it.agileday.data.DatabaseHelper;
import it.agileday.data.Session;
import it.agileday.data.Track;
import it.agileday.data.TrackRepository;

import java.util.ArrayList;

import com.google.android.apps.iosched.ui.widget.BlockView;
import com.google.android.apps.iosched.ui.widget.BlocksLayout;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

public class SessionActivityWithBlocksLayout extends Activity implements View.OnClickListener {
	@SuppressWarnings("unused")
	private static final String TAG = SessionActivityWithBlocksLayout.class.getName();

    private static final int DISABLED_BLOCK_ALPHA = 100;
	
	private ScrollView mScrollView;
	private BlocksLayout mBlocks;
	private View mNowView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blocks_content);

        //mTimeStart = getIntent().getLongExtra(EXTRA_TIME_START, mTimeStart);
        //mTimeEnd = getIntent().getLongExtra(EXTRA_TIME_END, mTimeEnd);

        mScrollView = (ScrollView) findViewById(R.id.blocks_scroll);
        mBlocks = (BlocksLayout) findViewById(R.id.blocks);
        mNowView = findViewById(R.id.blocks_now);

        mBlocks.setDrawingCacheEnabled(true);
        mBlocks.setAlwaysDrawnWithCacheEnabled(true);

        fillData();
	}

	private void fillData() {
		SQLiteDatabase database = new DatabaseHelper(this).getReadableDatabase();
		try {
			ArrayList<Track> tracks = new TrackRepository(database).getAll();
			int i = 0;
			for (Track track : tracks) {
				if (!track.isValid()) {
					throw new RuntimeException(track.validationMessage());
				}

		        mBlocks.removeAllBlocks();
		        
				for (Session session : track.getSessions()) {
					//final String type  = "workshop";
			        final Integer column = track.order; //sTypeColumnMap.get()
			        final String blockId = session.title;
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
