/*
	Copyright 2010 
	
	Author: Ilias Bartolini
	Author: Gian Marco Gherardi
	Author: Luigi Bozzo
	Author: Andrea Chiarini

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
import it.agileday.ui.sessions.ScheduleActivity;
import it.agileday.ui.speakers.SpeakersActivity;
import it.agileday.ui.twitter.TwitterActivity;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Config;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class ConferenceApp extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (Config.DEBUG && VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
			StrictMode.enableDefaults();
	    }
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	public void onSessionsClick(View v){
		startActivity(new Intent(this, ScheduleActivity.class));
	}
	
	public void onTwitterClick(View v){
		startActivity(new Intent(this, TwitterActivity.class));
	}
	
	public void onSpeakersClick(View v){
		startActivity(new Intent(this, SpeakersActivity.class));
	}
	
	public void onMapClick(View v){
		startMapActivity(44.416774, 8.853525, "Sheraton+Genova+Hotel+%26+Conference+Center+-+Via+Pionieri+ed+Aviatori+d%E2%80%99Italia,+44");
	}
	
	public void onDonateClick(View v){
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_xclick&business=donazioni%40agileday%2eit&no_shipping=2&no_note=1&tax=0&currency_code=EUR&bn=PP%2dDonationsBF&charset=UTF%2d8")));
	}
	
	private void startMapActivity(double latitude, double longitude, String query) {
		try {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + latitude + "," + longitude + "?z=18&q=" + query)));
		} catch (ActivityNotFoundException geoUrlNotSupported) {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/?ll=" + latitude + "," + longitude + "&z=18&q=" + query)));
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
}