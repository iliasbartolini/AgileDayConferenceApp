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

import java.util.List;

import it.agileday.R;
import it.aglieday.data.Session;
import it.aglieday.data.SessionsRepository;
import android.app.ListActivity;
import android.os.Bundle;

public class SessionActivity extends ListActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sessions);

		// String spreadsheetURL =
		// "http://spreadsheets.google.com/ccc?key=0AjjBWmINdN4HdDdickI3TWd5al9PMzdJQTUya1VtbXc&hl=en&authkey=COXmrYYB";
		String spreadsheetFeedURL = "http://spreadsheets.google.com/feeds/cells/0AjjBWmINdN4HdDdickI3TWd5al9PMzdJQTUya1VtbXc/od6/private/full";
		List<Session> mySessions = new SessionsRepository(spreadsheetFeedURL)
				.getSessions();

		SessionAdapter adapter = new SessionAdapter(this, mySessions);
		setListAdapter(adapter);
	}

}
