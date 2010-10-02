package it.agileday.ui.speakers;

import it.agileday.R;
import it.aglieday.data.Dao;
import it.aglieday.data.DatabaseHelper;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class SpeakersActivity extends Activity implements OnClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speakers);

		Dao dao = new Dao(new DatabaseHelper(this).getReadableDatabase());
		Cursor speakers = dao.fetchAllSpeakers();
		startManagingCursor(speakers);

		// Gallery g = (Gallery) findViewById(R.id.gallery);
		// g.setAdapter(new SpeakersCursorAdapter(this, speakers));

		ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper);

		Animation s_in = AnimationUtils.loadAnimation(this, R.anim.slidein);
		Animation s_out = AnimationUtils.loadAnimation(this, R.anim.slideout);
		flipper.setInAnimation(s_in);
		flipper.setOutAnimation(s_out);

		((Button) findViewById(R.id.button)).setOnClickListener(this);
	}

	private class SpeakersCursorAdapter extends CursorAdapter {
		public SpeakersCursorAdapter(Context context, Cursor c) {
			super(context, c);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			TextView name = (TextView) view.findViewById(R.id.name);
			name.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
			TextView bio = (TextView) view.findViewById(R.id.bio);
			bio.setMovementMethod(LinkMovementMethod.getInstance()); // http://code.google.com/p/android/issues/detail?id=2219
			bio.setText(Html.fromHtml(cursor.getString(cursor.getColumnIndexOrThrow("bio"))));
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View ret = getLayoutInflater().inflate(R.layout.speakers_item, parent, false);
			bindView(ret, context, cursor);
			return ret;
		}
	}

	@Override
	public void onClick(View v) {
		ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper);
		flipper.showNext();
	}
}
