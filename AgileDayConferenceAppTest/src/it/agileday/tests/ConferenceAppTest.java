/*
	Copyright 2010 
	
	Author: Ilias Bartolini
	Author: Luigi Bozzo

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

package it.agileday.tests;

import it.agileday.ui.ConferenceApp;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

public class ConferenceAppTest extends
		ActivityInstrumentationTestCase2<ConferenceApp> {

	private ConferenceApp homeActivity;
	
	public ConferenceAppTest() {
		super("it.agileday",ConferenceApp.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		homeActivity = this.getActivity();
	}

	public void preconditionsTest() {
		assertNotNull(homeActivity);		
	}

	public void checkButtonsOnHomeActivity() {
		Button sessionButton = (Button) homeActivity.findViewById(it.agileday.R.id.button_sessions);
		assertNotNull(sessionButton);
		
		Button donateButton = (Button) homeActivity.findViewById(it.agileday.R.id.button_donate);
		assertNotNull(donateButton);
		
		Button speakersButton = (Button) homeActivity.findViewById(it.agileday.R.id.button_speakers);
		assertNotNull(speakersButton);
		
		Button twitterButton = (Button) homeActivity.findViewById(it.agileday.R.id.button_twitter);
		assertNotNull(twitterButton);
		
		Button agileDayButton = (Button) homeActivity.findViewById(it.agileday.R.id.button_map);
		assertNotNull(agileDayButton);
	}
}
