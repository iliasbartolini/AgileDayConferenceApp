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

package it.agileday.ui;

import it.agileday.R;
import it.agileday.ui.sessions.SessionActivity;
import it.agileday.ui.speakers.SpeakersActivity;
import it.agileday.ui.twitter.TwitterActivity;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class ConferenceApp extends Activity implements OnClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		findViewById(R.id.button_sessions).setOnClickListener(this);
		findViewById(R.id.button_twitter).setOnClickListener(this);
		findViewById(R.id.button_speakers).setOnClickListener(this);
		findViewById(R.id.button_map).setOnClickListener(this);
		findViewById(R.id.button_donate).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_sessions:
			startActivity(new Intent(this, SessionActivity.class));
			break;
		case R.id.button_twitter:
			startActivity(new Intent(this, TwitterActivity.class));
			break;
		case R.id.button_speakers:
			startActivity(new Intent(this, SpeakersActivity.class));
			break;
		case R.id.button_map:
			openMapActivity(44.416774, 8.853525, "Sheraton+Genova+Hotel+%26+Conference+Center+-+Via+Pionieri+ed+Aviatori+d%E2%80%99Italia,+44");
			break;
		case R.id.button_donate:
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.agileday.it/front/sponsor/")));
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			startActivity(new Intent(this, About.class));
			break;
		case R.id.menu_www_agileday_it:
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.agileday.it")));
			break;
		default:
			return false;
		}
		return true;
	}

	private void openMapActivity(double latitude, double longitude, String query) {
		try {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + latitude + "," + longitude + "?z=18&q=" + query)));
		} catch (ActivityNotFoundException geoUrlNotSupported) {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/?ll=" + latitude + "," + longitude + "&z=18&q=" + query)));
		}
	}

}