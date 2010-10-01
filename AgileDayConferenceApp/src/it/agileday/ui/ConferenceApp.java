/*
   Copyright 2010 Ilias Bartolini

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
import android.content.Intent;
import android.os.Bundle;
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
		default:
			break;
		}
	}

}