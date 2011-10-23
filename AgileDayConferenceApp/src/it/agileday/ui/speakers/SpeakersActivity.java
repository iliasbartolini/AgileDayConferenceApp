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

package it.agileday.ui.speakers;

import it.agileday2011.R;
import it.agileday.data.DatabaseHelper;
import it.agileday.data.Speaker;
import it.agileday.data.SpeakerRepository;
import it.agileday.ui.TextViewUtil;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

public class SpeakersActivity extends Activity {
	static final String TAG = SpeakersActivity.class.getName();
	private ViewAnimator viewAnimator;
	private Gallery gallery;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speakers);
		viewAnimator = (ViewAnimator) findViewById(R.id.flipper);
		gallery = (Gallery) findViewById(R.id.gallery);

		List<Speaker> speakers = getSpeakers();

		gallery.setAdapter(new ImageAdapter(this, speakers));

		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				viewAnimator.setDisplayedChild(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		fillData(speakers);
	}

	private void fillData(List<Speaker> speakers) {
		for (Speaker speaker : speakers) {
			View view = getLayoutInflater().inflate(R.layout.speakers_item, viewAnimator, false);
			TextView name = (TextView) view.findViewById(R.id.name);
			name.setText(speaker.name);
			TextView bio = (TextView) view.findViewById(R.id.bio);
			TextViewUtil.setHtmlText(speaker.bio, bio);
			viewAnimator.addView(view);
		}
	}

	private List<Speaker> getSpeakers() {
		SQLiteDatabase database = new DatabaseHelper(this).getReadableDatabase();
		try {
			SpeakerRepository repo = new SpeakerRepository(database, this.getResources(), this);
			return repo.getAll();
		} finally {
			database.close();
		}
	}

	public class ImageAdapter extends BaseAdapter {
		int galleryItemBackground;
		private Context context;
		private final List<Speaker> speakers;

		public ImageAdapter(Context c, List<Speaker> speakers) {
			context = c;
			this.speakers = speakers;
			TypedArray a = obtainStyledAttributes(R.styleable.SpeakersGallery);
			galleryItemBackground = a.getResourceId(R.styleable.SpeakersGallery_android_galleryItemBackground, 0);
			a.recycle();
		}

		public int getCount() {
			return speakers.size();
		}

		public Object getItem(int position) {
			return speakers.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			float density = getResources().getDisplayMetrics().density;
			
			ImageView i = new ImageView(context);
			i.setImageDrawable(speakers.get(position).image);
			i.setLayoutParams(new Gallery.LayoutParams((int) (130 * density), (int) (150 * density)));
			i.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			i.setBackgroundResource(galleryItemBackground);
			return i;
		}
	}
}
