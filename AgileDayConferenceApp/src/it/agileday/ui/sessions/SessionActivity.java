/*
	Copyright 2010

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

package it.agileday.ui.sessions;

import it.agileday2011.R;
import it.agileday.data.DatabaseHelper;
import it.agileday.data.Session;
import it.agileday.data.SessionRepository;
import it.agileday.ui.TextViewUtil;
import it.agileday.utils.Dates;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class SessionActivity extends Activity {

	public final static String INTENT_EXTRA_KEY_SESSION_ID = "session_id";
	private static final String TIME_FORMAT = "HH:mm";
	
	private long sessionId = 0;
	private Session session;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.session);
		sessionId = getIntent().getLongExtra(INTENT_EXTRA_KEY_SESSION_ID, sessionId);

		loadSession();
		
		TextView title = (TextView) findViewById(R.id.session_title);
		title.setText(session.title);
		TextView speaker = (TextView) findViewById(R.id.session_speaker);
		speaker.setText(session.speakers);
		TextView time = (TextView) findViewById(R.id.session_time);
		time.setText(Dates.toString(session.getStart(), TIME_FORMAT) + " - " + Dates.toString(session.getEnd(), TIME_FORMAT));
		TextView description = (TextView) findViewById(R.id.session_description);
		TextViewUtil.setHtmlText(session.description, description);
		
	}

	private void loadSession() {
		SQLiteDatabase database = new DatabaseHelper(this).getReadableDatabase();
		SessionRepository repository = new SessionRepository(database, this);
		session = repository.getSessionFromId(sessionId);
	}
	
	
}
