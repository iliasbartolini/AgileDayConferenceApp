package it.agileday.ui.speakers;

import it.agileday.R;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;

public class SpeakersActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speakers);
		Gallery g = (Gallery) findViewById(R.id.gallery);
		g.setAdapter(new SpeakersAdapter());
	}

	public class SpeakersAdapter extends BaseAdapter {
		private final int[] colors = new int[] { Color.RED, Color.GREEN, Color.BLUE };

		@Override
		public int getCount() {
			return colors.length;
		}

		@Override
		public Object getItem(int arg0) {
			return colors[arg0];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View ret = (convertView == null ? getLayoutInflater().inflate(R.layout.speakers_item, parent, false) : convertView);
			ret.setBackgroundColor(colors[position]);
			return ret;
		}
	}
}
